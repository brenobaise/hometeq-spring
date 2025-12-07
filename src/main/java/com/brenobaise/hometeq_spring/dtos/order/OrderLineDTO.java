package com.brenobaise.hometeq_spring.dtos.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineDTO {

    private Long prodId;
    private Long quantityOrdered;
    private BigDecimal subTotal;
}
