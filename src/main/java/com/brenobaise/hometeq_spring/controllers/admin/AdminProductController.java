package com.brenobaise.hometeq_spring.controllers.admin;

import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductInsertDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductUpdateDTO;
import com.brenobaise.hometeq_spring.mappers.ProductMapper;
import com.brenobaise.hometeq_spring.services.ProductService;
import com.brenobaise.hometeq_spring.services.exceptions.DatabaseException;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        ProductDTO product = productService.newProduct(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getProdId())
                .toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO dto){
        ProductDTO updatedProduct = productService.updateProduct(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(updatedProduct.getProdId())
                .toUri();

        return ResponseEntity.created(uri).body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (productService.findById(id) == null) {
            throw new ResourceNotFoundException(id);
        }
        try {
            productService.deleteProduct(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential Integrity restraint, trying to delete used entity.");
        }

        return ResponseEntity.noContent().build();
    }
}
