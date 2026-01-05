package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.security.AppUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id){
        return "test";
    }
    @GetMapping("/me")
    public ResponseEntity<String> me(@AuthenticationPrincipal AppUserDetails user) {

        return ResponseEntity.ok(user.getUsername());
    }
}
