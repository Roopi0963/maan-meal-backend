package org.techtricks.artisanPlatform.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id" , nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id" , nullable = false)
    private Product product;

    private double price;

    private int quantity;


    public CartItem() {
    }

    public CartItem(Cart cart, Product product, double price, int quantity) {
        this.cart = cart;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }
}
