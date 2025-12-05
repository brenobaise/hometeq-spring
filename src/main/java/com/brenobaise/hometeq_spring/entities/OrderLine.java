package com.brenobaise.hometeq_spring.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tb_order_line")
@NoArgsConstructor
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineId;
    private int quantityOrdered;
    private BigDecimal subTotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderNo")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prodId")
    private Product product;

    public OrderLine(Order order, Product product, int quantityOrdered, BigDecimal subTotal) {
        this.order = order;
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.subTotal = subTotal;
    }
}
