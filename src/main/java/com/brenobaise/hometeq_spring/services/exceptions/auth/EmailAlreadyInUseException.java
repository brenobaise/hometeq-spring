package com.brenobaise.hometeq_spring.services.exceptions.auth;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super("The email " + message + " is already in use.");
    }
}
