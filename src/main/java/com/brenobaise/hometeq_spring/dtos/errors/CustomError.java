package com.brenobaise.hometeq_spring.dtos.errors;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class CustomError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
    private String message;
}
