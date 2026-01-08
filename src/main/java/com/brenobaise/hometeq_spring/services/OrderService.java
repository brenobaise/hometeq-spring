package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.dtos.order.ProductOrderItem;
import com.brenobaise.hometeq_spring.entities.Order;
import com.brenobaise.hometeq_spring.entities.OrderStatus;
import com.brenobaise.hometeq_spring.entities.Product;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.mappers.OrderMapper;
import com.brenobaise.hometeq_spring.repositories.OrderRepository;
import com.brenobaise.hometeq_spring.repositories.ProductRepository;
import com.brenobaise.hometeq_spring.services.exceptions.EmptyCartException;
import com.brenobaise.hometeq_spring.services.exceptions.OrderDoesNotExist;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import com.brenobaise.hometeq_spring.services.exceptions.products.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;

    public List<Order> getAllOrders(Long userId){
        return orderRepository.findOrdersWithLinesByUserId(userId);
    }

    @Transactional
    public OrderDTO getOrCreateDraft(String username){
        User user = userService.findByEmail(username);

        Order draft = orderRepository.findUserOrderByStatusWithDetails(user.getUserId(), OrderStatus.DRAFT)
                .orElseGet(() -> orderRepository.save(
                        new Order(user, LocalDateTime.now(), OrderStatus.DRAFT, null)
                ));
        draft.recalcTotal();
        return orderMapper.toDTO(draft);
    }

    @Transactional()
    public OrderDTO addItemToDraft(String username, ProductOrderItem item){
        User user = userService.findByEmail(username);

        // find a draft or create a new one
        Order draft = orderRepository
                .findUserOrderByStatusWithDetails(user.getUserId(), OrderStatus.DRAFT)
                .orElseGet(() -> orderRepository.save(
                        new Order(user,LocalDateTime.now(), OrderStatus.DRAFT,null)
                ));

        // get the product to from the database
        Product product = productRepository.findById(item.getProdId())
                .orElseThrow(() -> new ResourceNotFoundException(item.getProdId()));

        // checks stock
        if(product.getProdQuantity() != null && product.getProdQuantity() < item.getProdQuantity()){
            throw new InsufficientStockException(item.getProdId(), item.getProdQuantity());
        }


        draft.addOrIncrementProduct(product, item.getProdQuantity());
        draft.recalcTotal();
        return  orderMapper.toDTO(orderRepository.save(draft));
    }

    @Transactional
    public OrderDTO removeItemFromDraft(String username, Long prodId){
        User user = userService.findByEmail(username);

        Order draft = orderRepository
                .findUserOrderByStatusWithDetails(user.getUserId(), OrderStatus.DRAFT)
                .orElseThrow(EmptyCartException::new);

        draft.removeProduct(prodId);
        draft.recalcTotal();

        return  orderMapper.toDTO(orderRepository.save(draft));
    }

    @Transactional
    public OrderDTO checkoutDraft(String username){
        User user = userService.findByEmail(username);

        Order draft = orderRepository
                .findUserOrderByStatusWithDetails(user.getUserId(), OrderStatus.DRAFT)
                .orElseThrow(() -> new RuntimeException("No draft order"));

        if(draft.getOrderLineList().isEmpty()){
            throw new EmptyCartException();
        }

        // reduces stock per item in cart
        for(var line: draft.getOrderLineList()){
            Long prodID = line.getProduct().getProdId();
            long qty = line.getQuantityOrdered();

            int updated = productRepository.decrementStockIfAvailable(prodID,qty);

            if(updated == 0){
                throw new InsufficientStockException(prodID, qty);
            }
        }

        draft.setOrderStatus(OrderStatus.PENDING);
        draft.setOrderDate(LocalDateTime.now());
        draft.setShippingDate(LocalDate.now().plusDays(3));
        draft.recalcTotal();
        return orderMapper.toDTO(orderRepository.save(draft));

    }
}
