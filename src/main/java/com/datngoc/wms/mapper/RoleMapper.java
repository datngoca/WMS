package com.datngoc.wms.mapper;

import org.mapstruct.*;

import com.datngoc.wms.dto.request.RoleRequestDTO;
import com.datngoc.wms.entity.Role;

@Mapper
(
    componentModel = "spring",
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {

    Role toEntity(RoleRequestDTO roleRequestDTO);
    void updateEntityFromDTO(RoleRequestDTO roleRequestDTO, @MappingTarget Role role);
}
