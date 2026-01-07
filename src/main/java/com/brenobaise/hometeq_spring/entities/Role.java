package com.brenobaise.hometeq_spring.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_role")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)

    @Column(unique = true, nullable = false)
    private RoleName name;
}
