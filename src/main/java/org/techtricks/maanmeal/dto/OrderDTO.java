package org.techtricks.maanmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.techtricks.maanmeal.models.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class OrderDTO {
    private Long orderId;
    private String userEmail;
    private String userName;
    private LocalDateTime orderDate;
    private String status;
    private double totalAmount;
    private List<OrderItemDTO> orderItems;
    private String address;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = order.getTotalAmount();
        this.status = String.valueOf(order.getStatus());
        this.address = order.getAddress().toString();
        this.userEmail = order.getUser().getEmail();
        this.userName = order.getUser().getUsername();// Assuming `Address` has a `toString()`
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }
}
