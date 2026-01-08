package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderInsertDTO;
import com.brenobaise.hometeq_spring.entities.Order;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.services.OrderService;
import com.brenobaise.hometeq_spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderEmissionController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

}
