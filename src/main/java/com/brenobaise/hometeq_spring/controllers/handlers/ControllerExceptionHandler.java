package com.brenobaise.hometeq_spring.controllers.handlers;

import com.brenobaise.hometeq_spring.dtos.errors.CustomError;
import com.brenobaise.hometeq_spring.dtos.errors.ValidationError;
import com.brenobaise.hometeq_spring.services.exceptions.DatabaseException;
import com.brenobaise.hometeq_spring.services.exceptions.ProductAlreadyExistsException;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import com.brenobaise.hometeq_spring.services.exceptions.auth.EmailAlreadyInUseException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFoundHandler(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomError err = CustomError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Resource not found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseExceptionHandler(
            DatabaseException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomError err = CustomError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Database error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<CustomError> productAlreadyExistsExceptionHandler(
            ProductAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        CustomError err = CustomError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Conflict")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        ValidationError err = ValidationError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Validation error")
                .message("Invalid fields")
                .path(request.getRequestURI())
                .build();

        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomError> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomError err = CustomError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Malformed JSON")
                .message("The JSON sent by the client is invalid or incomplete.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomError> handleBadCredentialsException(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        CustomError err = CustomError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Unauthorized")
                .message("Incorrect username or password")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<CustomError> handlerEmailAlreadyInUseException (EmailAlreadyInUseException ex, HttpServletRequest request){
        CustomError err = CustomError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getMessage())
                .message("Email already in use.")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<?> handleInsufficientAuthenticationException (InsufficientAuthenticationException ex, HttpServletRequest request ){
        CustomError err = CustomError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .message("Insufficient stock")
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }
}
