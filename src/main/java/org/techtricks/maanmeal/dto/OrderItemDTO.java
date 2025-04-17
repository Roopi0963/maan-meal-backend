package org.techtricks.maanmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.techtricks.maanmeal.models.OrderItem;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long Id;
    private String productName;
    private int quantity;
    private double price;


    public OrderItemDTO(OrderItem orderItem) {
        this.Id = orderItem.getId();
        this.productName = orderItem.getProduct().getName(); // Assuming `OrderItem` has a `Product`
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getProductPrice();
    }
}
