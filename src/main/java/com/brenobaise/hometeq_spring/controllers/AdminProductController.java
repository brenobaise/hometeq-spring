package com.brenobaise.hometeq_spring.controllers;

import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductInsertDTO;
import com.brenobaise.hometeq_spring.entities.Product;
import com.brenobaise.hometeq_spring.mappers.ProductMapper;
import com.brenobaise.hometeq_spring.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public AdminProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductInsertDTO dto){
        Product product = productService.newProduct(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getProdId())
                .toUri();
        return ResponseEntity.created(uri).body(productMapper.toDTO(product));
    }
    public void update(){}
    public void delete(){}
}
