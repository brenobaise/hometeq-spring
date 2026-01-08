package com.brenobaise.hometeq_spring.entities;

import jakarta.persistence.*;
import lombok.Builder;
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
    private Long quantityOrdered;
    private BigDecimal subTotal;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderNo")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prodId")
    private Product product;

    public OrderLine(Order order, Product product, Long quantityOrdered, BigDecimal unitPrice, BigDecimal subTotal) {
        this.order = order;
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }
}
