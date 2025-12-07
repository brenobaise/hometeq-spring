package com.brenobaise.hometeq_spring.dtos.order;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductOrderItem {
    @NotNull(message = "Product ID cannot be null")
    private Long prodId;
    @Positive(message = "quantity must be a positive number") @Min(message = "Must choose quantity of at least 1", value = 1)
    private Long prodQuantity;
}
