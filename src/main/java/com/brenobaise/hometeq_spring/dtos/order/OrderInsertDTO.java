package com.brenobaise.hometeq_spring.dtos.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class OrderInsertDTO {
    @Valid
    List<ProductOrderItem> itemsInCart;
}
