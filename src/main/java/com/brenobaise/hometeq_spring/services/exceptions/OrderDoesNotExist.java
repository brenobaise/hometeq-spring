package com.brenobaise.hometeq_spring.services.exceptions;

public class OrderDoesNotExist extends ResourceNotFoundException {
    public OrderDoesNotExist(Long id) {
        super( id );
    }




}
