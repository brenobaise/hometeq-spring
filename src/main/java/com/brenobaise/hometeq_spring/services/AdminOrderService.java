package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.order.OrderAdminDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderStatusDTO;
import com.brenobaise.hometeq_spring.entities.Order;
import com.brenobaise.hometeq_spring.mappers.OrderMapper;
import com.brenobaise.hometeq_spring.repositories.OrderRepository;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public AdminOrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public Page<OrderAdminDTO> getAllOrders(Pageable pageable){
        return orderRepository.findAllOrders(pageable)
                .map(orderMapper::toAdminDTO);
    }

    @Transactional(readOnly = true)
    public OrderAdminDTO getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return orderMapper.toAdminDTO(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderAdminDTO> searchOrderByDate(LocalDate date, Pageable pageable){
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        Page<Order> orders = orderRepository.findByOrderDateRange(start, end, pageable);
        return orders.map(orderMapper::toAdminDTO);
    }

    @Transactional(readOnly = true)
    public Page<OrderStatusDTO> getOrdersByStatus(String status, Pageable pageable ){
        return orderRepository.findByOrderStatusIgnoreCase(status,pageable);
    }
}
