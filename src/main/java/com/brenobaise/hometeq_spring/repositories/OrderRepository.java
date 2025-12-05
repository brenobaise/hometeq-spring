package com.brenobaise.hometeq_spring.repositories;

import com.brenobaise.hometeq_spring.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
