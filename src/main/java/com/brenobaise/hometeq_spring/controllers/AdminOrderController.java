package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.order.OrderAdminDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.services.AdminOrderService;
import com.brenobaise.hometeq_spring.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
    @Autowired
    AdminOrderService adminOrderService;
    @GetMapping()
    public ResponseEntity<Page<OrderAdminDTO>> getAllOrders(Pageable pageable){
        return ResponseEntity.ok().body(adminOrderService.getAllOrders(pageable));
    }
}
