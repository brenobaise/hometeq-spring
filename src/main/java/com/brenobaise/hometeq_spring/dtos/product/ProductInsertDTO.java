package com.brenobaise.hometeq_spring.dtos.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInsertDTO {

    @NotBlank
    private String prodName;
    private String prodPicNameSmall;
    private String prodPicNameLarge;
    @NotBlank @Size(max = 50)
    private String prodDescriptionShort;
    @Size(max = 255)
    private String prodDescriptionLong;
    @Positive @NotNull
    private BigDecimal prodPrice;
    @PositiveOrZero
    private int prodQuantity;
}
