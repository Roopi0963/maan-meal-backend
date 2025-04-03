package org.techtricks.artisanPlatform.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    @PrePersist
    public void setOrderDate() {
        this.orderDate = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.EAGER) // ✅ Corrected from OneToOne to ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id") // ✅ Proper foreign key mapping
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Address address;
}
