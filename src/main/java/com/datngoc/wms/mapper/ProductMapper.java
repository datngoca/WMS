package com.datngoc.wms.mapper;

import org.mapstruct.*;

import com.datngoc.wms.dto.request.ProductRequestDTO;
import com.datngoc.wms.entity.Product;

@Mapper
    (
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    void updateEntityFromDTO(ProductRequestDTO dto, @MappingTarget Product entity);

    // ProductResponseDTO toDto(Product product);
}
