package com.brenobaise.hometeq_spring.repositories;

import com.brenobaise.hometeq_spring.entities.Role;
import com.brenobaise.hometeq_spring.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
