package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.dtos.order.ProductOrderItem;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final OrderService orderService;
    @GetMapping
    public ResponseEntity<OrderDTO> getDraft(@AuthenticationPrincipal AppUserDetails user) {
        return ResponseEntity.ok(orderService.getOrCreateDraft(user.getUsername()));
    }

    @PostMapping("/items")
    public ResponseEntity<OrderDTO> addItem(@AuthenticationPrincipal AppUserDetails user,
                                            @Valid @RequestBody ProductOrderItem item) {
        return ResponseEntity.ok(orderService.addItemToDraft(user.getUsername(), item));
    }

    @DeleteMapping("/items/{prodId}")
    public ResponseEntity<OrderDTO> removeItem(@AuthenticationPrincipal AppUserDetails user,
                                               @PathVariable Long prodId) {
        return ResponseEntity.ok(orderService.removeItemFromDraft(user.getUsername(), prodId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@AuthenticationPrincipal AppUserDetails user) {
        return ResponseEntity.ok(orderService.checkoutDraft(user.getUsername()));
    }
}
