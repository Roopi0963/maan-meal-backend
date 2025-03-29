package org.techtricks.artisanPlatform.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {


    private Long Id;
    private String name;
    private String imageName;
    private String description;
    private Integer stockQuantity;
    private double price;
    private double discount;
    private double specialPrice;

}
