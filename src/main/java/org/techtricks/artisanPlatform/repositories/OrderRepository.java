package org.techtricks.artisanPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techtricks.artisanPlatform.models.Order;
import org.techtricks.artisanPlatform.models.OrderStatus;
import org.techtricks.artisanPlatform.models.User;

import java.util.List;
import java.util.Optional;

@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);
    Optional<Order> findById(Long orderId);
}
