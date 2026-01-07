package com.brenobaise.hometeq_spring.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminSignUpRequest {
    @NotBlank
    @Size(max = 100)
    private String userFirstName;
    private String userPhoneNumber;
    @Email
    @NotBlank
    private String userEmail;
    @Size(min = 8, max=50, message = "Minimum 8 characters and maximum 50")
    private String userPassword;
    @NotBlank
    private String adminInviteCode;
}
