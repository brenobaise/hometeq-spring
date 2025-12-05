package com.brenobaise.hometeq_spring.services.exceptions;


public class ProductAlreadyExistsException extends DatabaseException {
    public ProductAlreadyExistsException(String productName) {
        super("Product already exists with name: " + productName);
    }
}
