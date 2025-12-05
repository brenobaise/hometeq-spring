package com.brenobaise.hometeq_spring.mappers;

import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

//    // PROJECTION ➜ DTO
//    ProductSummaryProjectionDTO toSummaryDTO(ProductSummaryProjection projection);
//
//    // LISTS
//    List<ProductSummaryProjectionDTO> toSummaryDTOList(List<ProductSummaryProjection> list);
}
