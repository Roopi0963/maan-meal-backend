package org.techtricks.maanmeal.services;


import org.springframework.stereotype.Service;
import org.techtricks.maanmeal.dto.OrderDTO;
import org.techtricks.maanmeal.dto.OrderSummaryDTO;
import org.techtricks.maanmeal.exceptions.CartNotFoundException;
import org.techtricks.maanmeal.exceptions.OrderNotFoundException;
import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.OrderStatus;

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
