package com.brenobaise.hometeq_spring.dtos.order;

import com.brenobaise.hometeq_spring.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {
    private Long orderNo;
    private OrderStatus orderStatus;
}
