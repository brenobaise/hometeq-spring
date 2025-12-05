package com.brenobaise.hometeq_spring.dtos.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private int prodId;
    private String prodName;
    private String prodPicNameSmall;
    private String prodPicNameLarge;
    private String prodDescriptionShort;
    private String prodDescriptionLong;
    private BigDecimal prodPrice;
    private int prodQuantity;
}
