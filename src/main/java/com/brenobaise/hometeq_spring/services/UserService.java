package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.repositories.UserRepository;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));
    }


}
