package com.brenobaise.hometeq_spring.mappers;

import com.brenobaise.hometeq_spring.dtos.order.OrderLineDTO;
import com.brenobaise.hometeq_spring.entities.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
    @Mapping(target = "prodId", source = "product.prodId")
    OrderLineDTO toDTO(OrderLine entity);
}
