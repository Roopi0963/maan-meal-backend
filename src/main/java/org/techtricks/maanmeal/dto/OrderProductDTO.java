package org.techtricks.maanmeal.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductDTO {
    private String productName;
    private String description;
    private Double price;
}
