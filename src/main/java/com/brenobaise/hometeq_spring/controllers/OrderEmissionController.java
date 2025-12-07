package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderInsertDTO;
import com.brenobaise.hometeq_spring.entities.Order;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.services.OrderService;
import com.brenobaise.hometeq_spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<OrderDTO> fireOrder( @Valid @RequestBody OrderInsertDTO order){
        // validate if user exists
        User user = userService.findByEmail(order.getUserEmail());


        OrderDTO newOrder = orderService.fire(user, order);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newOrder.getOrderNo())
                .toUri();
        return ResponseEntity.created(uri).body(newOrder);
    }
}
