package com.brenobaise.hometeq_spring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userFirstName;
    private String userSecondName;
    private String userAddress;
    private String userPostCode;
    private String userPhoneNumber;
    @Column(unique = true)
    private String userEmail;
    private String userPassword;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private Set<Order> orders  = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

}
