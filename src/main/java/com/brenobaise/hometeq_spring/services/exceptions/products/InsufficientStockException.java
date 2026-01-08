package com.brenobaise.hometeq_spring.services.exceptions.products;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long prodId, Long requested) {
        super("Insufficient stock for product " + prodId + " (requested " + requested + ")");
    }
}
