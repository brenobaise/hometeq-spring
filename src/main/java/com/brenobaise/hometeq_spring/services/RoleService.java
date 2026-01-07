package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.entities.Role;
import com.brenobaise.hometeq_spring.entities.RoleName;
import com.brenobaise.hometeq_spring.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role require(RoleName name){
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException("Missing role in database: " + name));

    }
}
