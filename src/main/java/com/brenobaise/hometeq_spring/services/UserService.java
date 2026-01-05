package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.auth.SignUpRequest;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.repositories.UserRepository;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import com.brenobaise.hometeq_spring.services.exceptions.auth.EmailAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User findByEmail(String email){
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));
    }

    @Transactional()
    public User registerUser(SignUpRequest request){
        String email = request.getUserEmail();

        if(userRepository.existsByUserEmail(email)){
            throw new EmailAlreadyInUseException(email);
        }

        User newUser = buildUserFromRequest(request);
        return userRepository.save(newUser);
    }

    private User buildUserFromRequest(SignUpRequest request) {
        return  User.builder()
                .userFirstName(request.getUserFirstName())
                .userSecondName(request.getUserSecondName())
                .userAddress(request.getUserAddress())
                .userPostCode(request.getUserPostCode())
                .userPhoneNumber(request.getUserPhoneNumber())
                .userEmail(request.getUserEmail())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .build();
    }


}
