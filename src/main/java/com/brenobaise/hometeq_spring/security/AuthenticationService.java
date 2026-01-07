package com.brenobaise.hometeq_spring.security;

import com.brenobaise.hometeq_spring.dtos.auth.AuthResponse;
import com.brenobaise.hometeq_spring.dtos.auth.UserSignUpRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String email, String password);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);
    Long getJwtExpiryMs();
    AuthResponse registerAndAuthenticateUser(UserSignUpRequest request);
}
