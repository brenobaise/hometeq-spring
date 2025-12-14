package com.brenobaise.hometeq_spring.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusDTO {
    private Long orderNo;
    private String orderStatus;
}
