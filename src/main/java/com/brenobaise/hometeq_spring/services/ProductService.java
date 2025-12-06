package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductInsertDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductUpdateDTO;
import com.brenobaise.hometeq_spring.entities.Product;
import com.brenobaise.hometeq_spring.mappers.ProductMapper;
import com.brenobaise.hometeq_spring.repositories.ProductRepository;
import com.brenobaise.hometeq_spring.services.exceptions.ProductAlreadyExistsException;
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

    @Transactional()
    public ProductDTO newProduct(ProductInsertDTO dto){
        if(productExistsByName(dto.getProdName())){
            throw new ProductAlreadyExistsException(dto.getProdName());
        }

        Product product = productMapper.toEntity(dto);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional()
    public ProductDTO updateProduct(Long id, ProductUpdateDTO dto){
        Product foundProduct = existsById(id);

        productMapper.updateEntityFromDto(dto, foundProduct);
        productRepository.save(foundProduct);
        return productMapper.toDTO(foundProduct);
    }

    @Transactional()
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    /**
     * Checks the database to see if a product already exists with a given name.\n
     * @param name query param to search for inside the database.
     * @return {@code boolean}
     */
    private boolean productExistsByName(String name){
        return productRepository.findByProdName(name).isPresent();
    }

    /**
     * Checks the database if a product exists by id.
     * This is an internal method for retrieving a Product entity.
     * @param id the id to look for in the database
     * @return a {@code Product} or {@code Optional#empty()}
     */
    private Product existsById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
