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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    private LocalDate shippingDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLineList = new ArrayList<>();

    public Order(User user, LocalDateTime orderDate, OrderStatus orderStatus, LocalDate shippingDate) {
        this.user = user;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.shippingDate = shippingDate;
        this.orderTotal = BigDecimal.ZERO;
    }

    public void addOrIncrementProduct(Product product, Long quantityToAdd) {
        OrderLine existing = orderLineList.stream()
                .filter(line -> line.getProduct().getProdId().equals(product.getProdId()))
                .findFirst()
                .orElse(null);

        if (existing == null) {
            BigDecimal unitPrice = product.getProdPrice();
            OrderLine line = new OrderLine(
                    this,
                    product,
                    quantityToAdd,
                    unitPrice,
                    unitPrice.multiply(BigDecimal.valueOf(quantityToAdd))
            );
            orderLineList.add(line);
        } else {
            long newQty = existing.getQuantityOrdered() + quantityToAdd;
            existing.setQuantityOrdered(newQty);
            existing.setSubTotal(existing.getUnitPrice().multiply(BigDecimal.valueOf(newQty)));
        }

        recalcTotal();
    }

    public void removeProduct(Long prodId) {
        orderLineList.removeIf(l -> l.getProduct().getProdId().equals(prodId));
        recalcTotal();
    }

    public void setProductQuantity(Long prodId, Long newQty) {
        if (newQty <= 0) {
            removeProduct(prodId);
            return;
        }

        OrderLine line = orderLineList.stream()
                .filter(l -> l.getProduct().getProdId().equals(prodId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not in order"));

        line.setQuantityOrdered(newQty);
        line.setSubTotal(line.getUnitPrice().multiply(BigDecimal.valueOf(newQty)));
        recalcTotal();
    }

    public void recalcTotal() {
        this.orderTotal = orderLineList.stream()
                .map(OrderLine::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateStatus(OrderStatus newStatus) {
        this.orderStatus = newStatus;
    }
}
