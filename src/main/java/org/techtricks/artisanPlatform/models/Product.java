package org.techtricks.artisanPlatform.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String brand;

    @Column(nullable = false)
    private BigDecimal price;

    private String category;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    private boolean productAvailable;

    private int stockQuantity;

    // Image fields
    private String imageName;

    private String imageType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;
}
