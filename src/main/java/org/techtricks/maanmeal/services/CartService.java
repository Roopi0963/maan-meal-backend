package org.techtricks.maanmeal.services;


import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.dto.CartDTO;
import org.techtricks.maanmeal.exceptions.ResourceNotFoundException;
import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.Cart;
import org.techtricks.maanmeal.models.User;

import java.util.List;

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
