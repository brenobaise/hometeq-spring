package com.brenobaise.hometeq_spring.controllers.handlers;

import com.brenobaise.hometeq_spring.dtos.errors.CustomError;
import com.brenobaise.hometeq_spring.dtos.errors.ValidationError;
import com.brenobaise.hometeq_spring.services.exceptions.DatabaseException;
import com.brenobaise.hometeq_spring.services.exceptions.ProductAlreadyExistsException;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    // to be able to print messages
//    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFoundHandler(ResourceNotFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseExceptionHandler(DatabaseException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Invalid camps", request.getRequestURI());

        for (FieldError f : ex.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<CustomError> productAlreadyExistsExceptionHandler(ProductAlreadyExistsException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError err = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomError> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new ValidationError(Instant.now(), status.value(),
                "Malformed JSON request: The JSON sent by the client is invalid or incomplete.", request.getRequestURI());


        return ResponseEntity.status(status).body(err);
    }


}
