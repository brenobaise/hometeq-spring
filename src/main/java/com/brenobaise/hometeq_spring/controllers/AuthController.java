package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.auth.AuthResponse;
import com.brenobaise.hometeq_spring.dtos.auth.LoginRequest;
import com.brenobaise.hometeq_spring.dtos.auth.SignUpRequest;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.security.AuthenticationService;
import com.brenobaise.hometeq_spring.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        String tokenValue = authenticationService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(authenticationService.getJwtExpiryMs()/1000)
                .build();
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        AuthResponse authResponse = authenticationService.registerAndAuthenticateUser(signUpRequest);

        return ResponseEntity.ok(authResponse);
    }
}
