package org.techtricks.artisanPlatform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techtricks.artisanPlatform.dto.OrderDTO;
import org.techtricks.artisanPlatform.dto.OrderSummaryDTO;
import org.techtricks.artisanPlatform.exceptions.CartNotFoundException;
import org.techtricks.artisanPlatform.exceptions.OrderNotFoundException;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.OrderStatus;
import org.techtricks.artisanPlatform.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/{userId}/{addressId}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long userId, @PathVariable Long addressId) throws UserNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(orderService.placeOrder(userId, addressId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }


    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @PathVariable OrderStatus status) throws OrderNotFoundException {
        orderService.updateOrderStatus(orderId, status );
        return ResponseEntity.ok().build();
    }

    @GetMapping({"/{orderId}/summary"})
    public OrderSummaryDTO getOrderSummary(@PathVariable Long orderId) {
        return orderService.getOrderSummary(orderId);
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

}
