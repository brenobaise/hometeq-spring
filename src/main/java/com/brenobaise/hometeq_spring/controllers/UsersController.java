package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.entities.Order;
import com.brenobaise.hometeq_spring.mappers.OrderMapper;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id){
        return "test";
    }
    @GetMapping("/me")
    public ResponseEntity<List<OrderDTO>> me(@AuthenticationPrincipal AppUserDetails user) {
        List<OrderDTO> orders = orderService.getAllOrders(user.getUserId())
                .stream().map(orderMapper::toDTO)
                .toList();

        return ResponseEntity.ok(orders);
    }
}
