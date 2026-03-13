package com.datngoc.wms.mapper;

import org.mapstruct.*;

import com.datngoc.wms.dto.request.WarehouseRequestDTO;
import com.datngoc.wms.entity.Warehouse;

@Mapper
(
    componentModel = "spring",
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
)
public interface WarehouseMapper {
    
    Warehouse toEntity(WarehouseRequestDTO warehouse);

    void updateEntityFromDTO(WarehouseRequestDTO warehouseRequestDTO, @MappingTarget Warehouse warehouse);
}
