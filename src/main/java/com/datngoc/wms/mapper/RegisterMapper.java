package com.datngoc.wms.mapper;

import org.mapstruct.*;

import com.datngoc.wms.dto.request.RegisterRequestDTO;
import com.datngoc.wms.dto.response.RegisterResponseDTO;
import com.datngoc.wms.entity.User;

@Mapper
(
    componentModel = "spring",
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
)
public interface RegisterMapper {
    User toEntity(RegisterRequestDTO requestDTO);

    RegisterResponseDTO toDto(User user);
}
