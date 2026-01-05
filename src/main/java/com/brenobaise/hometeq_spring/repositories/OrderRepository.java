package com.brenobaise.hometeq_spring.repositories;

import com.brenobaise.hometeq_spring.dtos.order.OrderStatusDTO;
import com.brenobaise.hometeq_spring.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
           SELECT o FROM Order o
           ORDER BY o.orderDate DESC
        """)
    Page<Order> findAllOrders(Pageable pageable);

    @Query("""
           SELECT o FROM Order o
           WHERE o.user.userEmail = :email
           ORDER BY o.orderDate DESC
        """)
    Page<Order> findByUserEmail(String email, Pageable pageable);

    @Query("""
           SELECT DISTINCT o FROM Order o
           LEFT JOIN FETCH o.orderLineList l
           LEFT JOIN FETCH l.product
           WHERE o.orderNo = :id
        """)
    Optional<Order> findOrderWithLines(Long id);
    @Query("""
           SELECT DISTINCT o FROM Order o
           LEFT JOIN FETCH o.orderLineList l
           LEFT JOIN FETCH l.product
           WHERE o.user.userId = :userId
           ORDER BY o.orderDate DESC
        """)
    List<Order> findOrdersWithLinesByUserId(@Param("userId") Long userId);


    @Query("""
           SELECT DISTINCT o FROM Order o
           LEFT JOIN FETCH o.orderLineList l
           LEFT JOIN FETCH l.product
           WHERE o.user.userEmail = :email
           ORDER BY o.orderDate DESC
        """)
    List<Order> findUserOrdersWithDetails(String email);

    @Query("""
            SELECT o FROM Order o
            WHERE o.orderDate BETWEEN :start AND :end
            ORDER BY o.orderDate DESC
            """)
    Page<Order> findByOrderDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                     Pageable pageable);

    @Query("""
            SELECT new com.brenobaise.hometeq_spring.dtos.order.OrderStatusDTO(
                    o.orderNo,
                    o.orderStatus
                )
            FROM Order o
            Where LOWER(o.orderStatus) = LOWER(:status)
            """)
    Page<OrderStatusDTO> findByOrderStatusIgnoreCase(
            @Param( "status") String status, Pageable pageable
    );

}
