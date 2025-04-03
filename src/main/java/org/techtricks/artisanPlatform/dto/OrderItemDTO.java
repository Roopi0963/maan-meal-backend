package org.techtricks.artisanPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long Id;
    private String productName;
    private int quantity;
    private double price;

}
