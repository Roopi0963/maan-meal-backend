package org.techtricks.artisanPlatform.services;


import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.dto.OrderDTO;
import org.techtricks.artisanPlatform.dto.OrderSummaryDTO;
import org.techtricks.artisanPlatform.exceptions.CartNotFoundException;
import org.techtricks.artisanPlatform.exceptions.OrderNotFoundException;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.Order;
import org.techtricks.artisanPlatform.models.OrderStatus;

import java.util.List;

@Service
public interface OrderService  {

    public OrderDTO placeOrder(Long userId, Long addressId) throws UserNotFoundException, CartNotFoundException;

    public List<OrderDTO> getOrdersByUser(Long userId);
 //   Optional<Order> updateOrderStatus(Long orderId, OrderStatus status);


    public OrderSummaryDTO getOrderSummary(Long orderId);

    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderNotFoundException;

    public List<OrderDTO> getAllOrders();
}
