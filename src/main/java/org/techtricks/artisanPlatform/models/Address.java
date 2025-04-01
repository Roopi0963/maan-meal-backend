package org.techtricks.artisanPlatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"}) // Prevent recursion
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Use IDENTITY for consistency
    private Long id;

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ Lazy loading to optimize performance
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
