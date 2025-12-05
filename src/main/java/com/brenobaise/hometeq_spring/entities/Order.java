package com.brenobaise.hometeq_spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tb_orders")
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

    @OneToMany(mappedBy = "order")
    List<OrderLine> orderLineList = new ArrayList<>();


    public void addProduct(Product product, int quantity) {
        OrderLine line = new OrderLine(
                this, product,quantity, product.getProdPrice().multiply(BigDecimal.valueOf(quantity))
        );

        this.orderLineList.add(line);
    }

}

