package com.datngoc.wms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.datngoc.wms.dto.request.ProductUnitRequestDTO;
import com.datngoc.wms.entity.ProductUnit;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductUnitMapper {
    ProductUnit toEntity(ProductUnitRequestDTO requestDTO);

    void updateEntityFromDTO(ProductUnitRequestDTO requestDTO, @MappingTarget ProductUnit productUnit);
}
