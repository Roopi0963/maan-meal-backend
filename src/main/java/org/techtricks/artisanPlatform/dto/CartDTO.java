package org.techtricks.artisanPlatform.dto;

import lombok.*;
import org.techtricks.artisanPlatform.models.Product;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
public class CartDTO {

    private Long cartId;
    private Double totalPrice;
    private List<ProductDTO> product = new ArrayList<>();
}
