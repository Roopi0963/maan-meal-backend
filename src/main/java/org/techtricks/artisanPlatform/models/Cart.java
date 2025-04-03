package org.techtricks.artisanPlatform.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "cartItems")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    private Double totalPrice = 0.0;
}
