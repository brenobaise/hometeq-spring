package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.entities.Product;
import com.brenobaise.hometeq_spring.mappers.ProductMapper;
import com.brenobaise.hometeq_spring.repositories.ProductRepository;
import com.brenobaise.hometeq_spring.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return productMapper.toDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllByName(String name, Pageable pageable){
        Page<Product> result = productRepository.searchByName(name, pageable);
        return result.map(productMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable){
        Page<Product> result = productRepository.findAll(pageable);

        return result.map(productMapper::toDTO);
    }

}
