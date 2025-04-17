package org.techtricks.maanmeal.dto;

import lombok.*;

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
