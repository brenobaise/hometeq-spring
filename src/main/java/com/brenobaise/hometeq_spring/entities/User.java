package com.brenobaise.hometeq_spring.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private int userType;
    private String userFirstName;
    private String userSecondName;
    private String userAddress;
    private String userPostCode;
    private int userPhoneNumber;
    @Column(unique = true)
    private String userEmail;
    private String userPassword;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders  = new HashSet<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

}
