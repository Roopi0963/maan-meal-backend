package org.techtricks.artisanPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.techtricks.artisanPlatform.models.Cart;
import org.techtricks.artisanPlatform.models.User;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);


    @Query("SELECT c FROM Cart c WHERE c.user.email = :email")
    Cart findCartByEmail(@Param("email") String email);


    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Cart> findCartsByProductId(Long productId);

    User user(User user);
}
