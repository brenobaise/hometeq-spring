package com.brenobaise.hometeq_spring.services.admin;

import com.brenobaise.hometeq_spring.dtos.auth.AdminSignUpRequest;
import com.brenobaise.hometeq_spring.dtos.auth.AuthResponse;
import com.brenobaise.hometeq_spring.entities.RoleName;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.security.AuthenticationService;
import com.brenobaise.hometeq_spring.services.UserService;
import com.brenobaise.hometeq_spring.services.exceptions.InvalidAdminInviteCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    @Value("${admin.invite.code}")
    private String adminInviteCode;

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthResponse registerAdmin(AdminSignUpRequest request) {
        if (!adminInviteCode.equals(request.getAdminInviteCode())) {
            throw new InvalidAdminInviteCodeException();
        }

        User registeredAdmin = userService.registerAdmin(request);

        String token = authenticationService.generateToken(new AppUserDetails(registeredAdmin));

        return new AuthResponse(token,authenticationService.getJwtExpiryMs() /1000);
    }

    }


