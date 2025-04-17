package org.techtricks.maanmeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techtricks.maanmeal.models.OrderItem;

public interface OrderItemRepository extends JpaRepository <OrderItem, Long> {
}
