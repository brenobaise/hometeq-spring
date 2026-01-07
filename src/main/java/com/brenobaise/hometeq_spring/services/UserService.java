package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.auth.AdminSignUpRequest;
import com.brenobaise.hometeq_spring.dtos.auth.UserSignUpRequest;
import com.brenobaise.hometeq_spring.entities.Role;
import com.brenobaise.hometeq_spring.entities.RoleName;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.repositories.UserRepository;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import com.brenobaise.hometeq_spring.services.exceptions.auth.EmailAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleService roleService;
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
    public User registerUser(UserSignUpRequest request){
        checkIfEmailAlreadyExists(request.getUserEmail());

        User newUser = buildUserFromRequest(request);
        return userRepository.save(newUser);
    }
    public User registerAdmin(AdminSignUpRequest request){
        checkIfEmailAlreadyExists(request.getUserEmail());

        Role defaultRole = roleService.require(RoleName.ROLE_ADMIN);

        User admin = User.builder()
                .userFirstName(request.getUserFirstName())
                .userEmail(request.getUserEmail())
                .userPhoneNumber(request.getUserPhoneNumber())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .roles(Set.of(defaultRole))
                .build();

        return userRepository.save(admin);
    }


    private void checkIfEmailAlreadyExists(String email) {
        if(userRepository.existsByUserEmail(email)){
            throw new EmailAlreadyInUseException(email);
        }
    }


    private User buildUserFromRequest(UserSignUpRequest request) {
        Role defaultRole = roleService.require(RoleName.ROLE_USER);
        return  User.builder()
                .userFirstName(request.getUserFirstName())
                .userSecondName(request.getUserSecondName())
                .userAddress(request.getUserAddress())
                .userPostCode(request.getUserPostCode())
                .userPhoneNumber(request.getUserPhoneNumber())
                .userEmail(request.getUserEmail())
                .roles(Set.of(defaultRole))
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .build();
    }


}
