package com.brenobaise.hometeq_spring.dtos.order;

import com.brenobaise.hometeq_spring.entities.OrderLine;
import com.brenobaise.hometeq_spring.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderAdminDTO {
    private Long orderNo;
    private LocalDateTime orderDate;
    private BigDecimal orderTotal;
    private String orderStatus;
    private LocalDate shippingDate;

    private String userEmail;

    @JsonProperty("boughtProducts")
    private List<OrderLineDTO> items;
}
