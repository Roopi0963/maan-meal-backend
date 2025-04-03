package org.techtricks.artisanPlatform.dto;
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
