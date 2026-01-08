package com.brenobaise.hometeq_spring.services.exceptions;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException() {
        super("The cart is empty");
    }
}
