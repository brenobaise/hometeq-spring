package com.brenobaise.hometeq_spring.dtos.order;

import lombok.Data;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long orderNo;
    private BigDecimal orderTotal;
    private LocalDateTime orderDate;
    private List<OrderLineDTO> items;

}
