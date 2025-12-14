package com.brenobaise.hometeq_spring.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tb_orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNo;
    private LocalDateTime orderDate;
    private BigDecimal orderTotal;
    private String orderStatus;
    private LocalDate shippingDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderLine> orderLineList = new ArrayList<>();


    public Order(User user, LocalDateTime orderDate, String orderStatus, LocalDate shippingDate) {
        this.user = user;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.shippingDate = shippingDate;
    }

    public void addProduct(Product product, Long quantity) {
        OrderLine line = new OrderLine(
                this, product,quantity, product.getProdPrice().multiply(BigDecimal.valueOf(quantity))
        );

        line.setOrder(this);
        line.setProduct(product);

        this.orderLineList.add(line);
    }

    public void updateStatus(String newStatus) {
        setOrderStatus(newStatus);
    }
}

