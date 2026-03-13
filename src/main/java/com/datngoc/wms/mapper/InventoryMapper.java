package com.datngoc.wms.mapper;

import org.mapstruct.*;

import com.datngoc.wms.dto.request.InventoryRequestDTO;
import com.datngoc.wms.entity.Inventory;


@Mapper
    (
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
    )
public interface InventoryMapper {
    
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);

    void updateEntityFromDTO(InventoryRequestDTO inventoryRequestDTO, @MappingTarget Inventory inventory);
}
