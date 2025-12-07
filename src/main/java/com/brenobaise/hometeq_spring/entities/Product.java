package com.brenobaise.hometeq_spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tb_products")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;

    @Column(unique = true)
    private String prodName;
    @Column(nullable = true)
    private String prodPicNameSmall;
    @Column(nullable = true)
    private String prodPicNameLarge;
    @Column(nullable = true)
    private String prodDescriptionShort;
    @Column(nullable = true)
    private String prodDescriptionLong;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prodPrice;
    @Column(nullable = true)
    private Long prodQuantity;

    @OneToMany(mappedBy = "product")
    List<OrderLine> orderLineList = new ArrayList<>();

    public Product(String prodName, String prodPicNameSmall, String prodPicNameLarge, String prodDescriptionShort, String prodDescriptionLong, BigDecimal prodPrice, Long prodQuantity) {
        this.prodName = prodName;
        this.prodPicNameSmall = prodPicNameSmall;
        this.prodPicNameLarge = prodPicNameLarge;
        this.prodDescriptionShort = prodDescriptionShort;
        this.prodDescriptionLong = prodDescriptionLong;
        this.prodPrice = prodPrice;
        this.prodQuantity = prodQuantity;
    }
}
