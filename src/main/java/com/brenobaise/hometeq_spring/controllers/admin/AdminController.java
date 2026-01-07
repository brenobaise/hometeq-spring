package com.brenobaise.hometeq_spring.controllers.admin;

import com.brenobaise.hometeq_spring.dtos.auth.AdminSignUpRequest;
import com.brenobaise.hometeq_spring.dtos.auth.AuthResponse;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.services.admin.AdminAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminAuthService adminAuthService;

    @GetMapping()
    public ResponseEntity<String> me(@AuthenticationPrincipal AppUserDetails userDetails){
        return ResponseEntity.ok("validated");
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid AdminSignUpRequest signUpRequest){
        AuthResponse authResponse = adminAuthService.registerAdmin(signUpRequest);

        return ResponseEntity.ok(authResponse);
    }
}
