package com.brenobaise.hometeq_spring.dtos.errors;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ValidationError extends  CustomError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path, String message) {
        super(timestamp, status, error, path, message);
    }

    public void addError(String fieldName, String message){
        errors.add( new FieldMessage(fieldName, message));
    }
}
