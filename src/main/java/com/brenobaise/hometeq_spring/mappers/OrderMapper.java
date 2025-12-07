package com.brenobaise.hometeq_spring.mappers;

import com.brenobaise.hometeq_spring.dtos.order.OrderDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderInsertDTO;
import com.brenobaise.hometeq_spring.dtos.order.OrderLineDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductInsertDTO;
import com.brenobaise.hometeq_spring.dtos.product.ProductUpdateDTO;
import com.brenobaise.hometeq_spring.entities.Order;
import com.brenobaise.hometeq_spring.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {OrderLineMapper.class})
public interface OrderMapper {

    @Mapping(target = "orderNo", ignore = true)
    Order toEntity(OrderInsertDTO dto);

    @Mapping(target = "items", source = "orderLineList")
    OrderDTO toDTO(Order order);
}
