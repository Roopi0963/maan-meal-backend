package org.techtricks.artisanPlatform.services;


import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.dto.CartDTO;
import org.techtricks.artisanPlatform.exceptions.ResourceNotFoundException;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.Cart;
import org.techtricks.artisanPlatform.models.User;

import java.util.List;
import java.util.Optional;

@Service
public interface CartService {

    public Cart createCartForUser(User user);
    CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) throws ResourceNotFoundException, UserNotFoundException;


    List<CartDTO> getAllCarts();

    public CartDTO getCart(Long id) throws ResourceNotFoundException;

    CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) throws ResourceNotFoundException;

    void updateProductInCarts(Long cartId, Long productId) throws ResourceNotFoundException;

    String deleteProductFromCart(Long cartId, Long productId) throws ResourceNotFoundException;
}
