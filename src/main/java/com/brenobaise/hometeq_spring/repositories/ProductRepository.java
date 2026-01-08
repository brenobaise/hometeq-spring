package com.brenobaise.hometeq_spring.repositories;

import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("""
        UPDATE Product p
        SET p.prodQuantity = p.prodQuantity - :qty
        WHERE p.prodId = :prodId AND p.prodQuantity >= :qty
    """)
    int decrementStockIfAvailable(@Param("prodId") Long prodId, @Param("qty") Long qty);

    @Query("""
       SELECT p 
       FROM Product p
       WHERE UPPER(p.prodName) LIKE UPPER(CONCAT('%', :name, '%'))
       """)
    Page<Product> searchByName(@Param("name") String name, Pageable pageable);


    Page<Product> findAll(Pageable pageable);

    Optional<Product> findByProdName(String name);

    @Query("SELECT p FROM Product p WHERE p.prodId IN :ids")
    List<Product> findAllByProdIds(List<Long> ids);


}
