package org.techtricks.artisanPlatform.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSummaryDTO {

    private Long orderId;
    private String orderStatus;
    private Double totalAmount;
    private String orderDate;

    private String userName;
    private String userEmail;

    private String addressStreet;
    private String addressCity;
    private String addressState;
    private String addressZip;
    private String addressCountry;

    private List<OrderProductDTO> products;
}
