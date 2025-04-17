package org.techtricks.maanmeal.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.dto.CartDTO;
import org.techtricks.maanmeal.dto.ProductDTO;
import org.techtricks.maanmeal.exceptions.APIException;
import org.techtricks.maanmeal.exceptions.ResourceNotFoundException;
import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.Cart;
import org.techtricks.maanmeal.models.CartItem;
import org.techtricks.maanmeal.models.Product;
import org.techtricks.maanmeal.models.User;
import org.techtricks.maanmeal.repositories.CartItemRepository;
import org.techtricks.maanmeal.repositories.CartRepository;
import org.techtricks.maanmeal.repositories.ProductRepository;
import org.techtricks.maanmeal.repositories.UserRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService {


    private  final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0); // Initialize total price to 0
        cart.setCartItems(new ArrayList<>()); // Initialize empty cart items list
        return cartRepository.save(cart);
    }
    @Override
    public CartDTO addProductToCart(Long userId, Long productId, Integer quantity) throws ResourceNotFoundException, UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Check if cart exists, otherwise create one
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> createCartForUser(user));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Check product stock availability
        if (product.getStockQuantity() < quantity) {
            throw new APIException("Insufficient stock for " + product.getName() + ". Available: " + product.getStockQuantity());
        }

        // Check if product is already in the cart
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setProductPrice(product.getPrice() * cartItem.getQuantity());  // ✅ Correct price calculation
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setDiscount(0.0);
            newCartItem.setProductPrice(product.getPrice() * quantity);

            cartItemRepository.save(newCartItem); // Save the new cart item first
            cart.getCartItems().add(newCartItem); // Then add it to the cart

            cartRepository.save(cart); // Save cart again to ensure relationships persist
        }


        // Reduce product stock
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);  // Save updated stock

        System.out.println("Product in CartItem: " + product);
        System.out.println("Cart before saving: " + cart);

        // ✅ Update total price correctly
        cart.setTotalPrice(cart.getCartItems().stream()
                .map(CartItem::getProductPrice)
                .reduce(0.0, Double::sum));  // ✅ Safer sum calculation

        cartRepository.save(cart);  // Save updated cart

        // Convert to DTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOs = cart.getCartItems().stream()
                .filter(p -> p.getProduct() != null)  // ✅ Skip null products
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                .collect(Collectors.toList());

        cartDTO.setProduct(productDTOs);
        return cartDTO;
    }



    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository .findAll();

        if (carts.isEmpty()) {
            throw new APIException("No cart exists");
        }

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProduct(products);

            return cartDTO;

        }).collect(Collectors.toList());

        return cartDTOs;
    }



    @Override public CartDTO getCart(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepository.findCartByEmail(id);

        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "userId",id);
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                .collect(Collectors.toList());

        cartDTO.setProduct(products);

        return cartDTO;
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) throws ResourceNotFoundException {
        // Fetch cart and product
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Find existing cart item
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
        if (cartItem == null) {
            throw new APIException("Product " + product.getName() + " is not in the cart!");
        }

        int previousQuantity = cartItem.getQuantity();
        int availableStock = product.getStockQuantity() + previousQuantity;

        // Ensure requested quantity is not more than available stock
        if (quantity > availableStock) {
            throw new APIException("Not enough stock for " + product.getName() + ". Available: " + availableStock);
        }

        // Update cart item quantity and price before adjusting stock
        cartItem.setQuantity(quantity);
        cartItem.setProductPrice(product.getPrice() * quantity);  // Fix: Correct price calculation
        cartItemRepository.save(cartItem);

        // Update product stock after cartItem update
        product.setStockQuantity(availableStock - quantity);
        productRepository.save(product);

        // Update cart total price efficiently
        cart.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(item -> item.getProductPrice())
                .sum());
        cartRepository.save(cart);

        // Convert to DTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<ProductDTO> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                .collect(Collectors.toList());
        cartDTO.setProduct(productDTOs);

        return cartDTO;
    }




    @Override
    public void updateProductInCarts(Long cartId, Long productId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getName() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getPrice());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItem = cartItemRepository .save(cartItem);
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        // Update cart total price before deleting item
        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

        // Return product stock
        Product product = cartItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());
        productRepository.save(product); // Save product with updated stock

        // Delete cart item
        cartItemRepository.delete(cartItem);

        // Save updated cart
        cartRepository.save(cart);

        return "Product " + cartItem.getProduct().getName() + " removed from the cart!";
    }

}
