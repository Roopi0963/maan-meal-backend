package org.techtricks.artisanPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techtricks.artisanPlatform.models.OrderItem;

public interface OrderItemRepository extends JpaRepository <OrderItem, Long> {
}
