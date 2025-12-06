package com.brenobaise.hometeq_spring.services.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Entity with id: " + id + " was not found");
    }

    public ResourceNotFoundException(String entityName) {
        super("Entity with name: " + entityName + " was not found");
    }
}
