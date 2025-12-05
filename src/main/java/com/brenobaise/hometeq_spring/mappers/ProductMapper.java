package com.brenobaise.hometeq_spring.mappers;

import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductInsertDTO;
import com.brenobaise.hometeq_spring.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    // DTO ➜ ENTITY (for create)
    @Mapping(target = "prodId", ignore = true)
    Product toEntity(ProductInsertDTO dto);

//    // DTO ➜ ENTITY (for update)
//    void updateEntity(ProductUpdateDTO dto, @MappingTarget Product entity);

//    // PROJECTION ➜ DTO
//    ProductSummaryProjectionDTO toSummaryDTO(ProductSummaryProjection projection);
//
//    // LISTS
//    List<ProductSummaryProjectionDTO> toSummaryDTOList(List<ProductSummaryProjection> list);
}
