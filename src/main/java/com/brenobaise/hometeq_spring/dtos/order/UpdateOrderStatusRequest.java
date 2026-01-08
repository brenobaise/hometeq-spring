package com.brenobaise.hometeq_spring.dtos.order;

import com.brenobaise.hometeq_spring.entities.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    OrderStatus orderStatus;
}
