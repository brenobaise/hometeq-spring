package com.brenobaise.hometeq_spring.dtos.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateDTO {

    private String prodName;
    private String prodPicNameSmall;
    private String prodPicNameLarge;
    @Size(max = 50)
    private String prodDescriptionShort;
    @Size(max = 255)
    private String prodDescriptionLong;
    @Positive
    private BigDecimal prodPrice;
    private int prodQuantity;
}
