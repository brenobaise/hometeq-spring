package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.order.OrderAdminDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderStatusDTO;
import com.brenobaise.hometeq_spring.dtos.order.UpdateOrderStatusRequest;
import com.brenobaise.hometeq_spring.services.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
    @Autowired
    AdminOrderService adminOrderService;
    @GetMapping()
    public ResponseEntity<Page<OrderAdminDTO>> getAllOrders(Pageable pageable){
        return ResponseEntity.ok().body(adminOrderService.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderAdminDTO> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok().body(adminOrderService.getOrderById(id));
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<Page<OrderAdminDTO>> searchOrdersByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Pageable pageable) {

        Page<OrderAdminDTO> result = adminOrderService.searchOrderByDate(date, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<OrderStatusDTO>> getOrdersByStatus(
            // missing to fix null attributes inside projection dto
            @PathVariable String status, Pageable pageable){
        return ResponseEntity.ok(adminOrderService.getOrdersByStatus(status.toLowerCase(), pageable));
    }

    @PatchMapping("{orderNo}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderNo, @RequestBody UpdateOrderStatusRequest request){
        adminOrderService.updateOrderStatus(orderNo, request.getStatus());
        return  ResponseEntity.noContent().build();

    }


}
