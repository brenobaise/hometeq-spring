package com.brenobaise.hometeq_spring.dtos.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class OrderInsertDTO {
    @NotBlank(message = "user email cannot be blank")
    @Email(message = "email must be a valid email")
    private String userEmail;
    @Valid
    List<ProductOrderItem> itemsInCart;
}
