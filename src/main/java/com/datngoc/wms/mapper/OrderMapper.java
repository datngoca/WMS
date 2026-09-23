package com.datngoc.wms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.datngoc.wms.dto.response.OrderItemResponseDTO;
import com.datngoc.wms.dto.response.OrderResponseDTO;
import com.datngoc.wms.entity.Order;
import com.datngoc.wms.entity.OrderItem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    OrderResponseDTO toDto(Order order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productSku", source = "product.sku")
    OrderItemResponseDTO toItemDto(OrderItem item);
}
