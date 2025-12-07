package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.order.OrderAdminDTO;
import com.brenobaise.hometeq_spring.mappers.OrderMapper;
import com.brenobaise.hometeq_spring.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
