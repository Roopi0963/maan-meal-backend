package org.techtricks.maanmeal.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.techtricks.maanmeal.models.Cart;
import org.techtricks.maanmeal.models.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    //Optional<Cart> findByUser(User user);


    @Query("SELECT c FROM Cart c WHERE c.user.id = :id")
    Cart findCartByEmail(@Param("id") Long id);


    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Cart> findCartsByProductId(Long productId);

    User user(User user);

    @EntityGraph(attributePaths = "cartItems")
    Optional<Cart> findByUser(User user);
}