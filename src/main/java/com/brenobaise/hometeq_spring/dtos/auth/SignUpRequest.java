package com.brenobaise.hometeq_spring.dtos.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {
    @NotBlank @Size(max = 100)
    private String userFirstName;
    @Size(max = 255)
    private String userSecondName;
    @Size(max = 255)
    private String userAddress;
    @Size(max = 255)
    private String userPostCode;
    private String userPhoneNumber;
    @Email @NotBlank
    private String userEmail;
    @Size(min = 8, max=50, message = "Minimum 8 characters and maximum 50")
    private String userPassword;
}
