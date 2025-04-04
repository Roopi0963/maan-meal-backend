package org.techtricks.artisanPlatform.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id" , nullable = false ,foreignKey = @ForeignKey(name = "fK_order_item_product"))
    private Product product;

    private int quantity;

    private double productPrice;
}
