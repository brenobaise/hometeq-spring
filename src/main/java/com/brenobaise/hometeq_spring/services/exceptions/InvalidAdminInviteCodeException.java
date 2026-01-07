package com.brenobaise.hometeq_spring.services.exceptions;

public class InvalidAdminInviteCodeException extends RuntimeException {
    public InvalidAdminInviteCodeException() {
        super("Invalid admin invite code");
    }
}
