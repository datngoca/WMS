package com.datngoc.wms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.datngoc.wms.dto.request.UnitRequestDTO;
import com.datngoc.wms.entity.Unit;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface UnitMapper {
    Unit toEntity(UnitRequestDTO requestDTO);

    void updateEntity(UnitRequestDTO requestDTO, @MappingTarget Unit unit);
}
