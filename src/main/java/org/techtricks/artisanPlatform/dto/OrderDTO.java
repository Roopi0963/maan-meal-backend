package org.techtricks.artisanPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class OrderDTO {
    private Long orderId;
    private Date orderDate;
    private String status;
    private double totalAmount;
    private List<OrderItemDTO> orderItems;
    private String address;
}
