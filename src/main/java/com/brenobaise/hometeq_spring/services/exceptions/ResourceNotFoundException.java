package com.brenobaise.hometeq_spring.services.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Entity with id: " + id + " was not found");
    }
}
