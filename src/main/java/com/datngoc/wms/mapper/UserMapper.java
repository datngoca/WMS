package com.datngoc.wms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.datngoc.wms.dto.request.AdminUserRequest;
import com.datngoc.wms.dto.response.AdminUserResponseDTO;
import com.datngoc.wms.entity.User;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    User toEntity(AdminUserRequest adminUserRequestDTO);

    void updateEntityFromDTO(AdminUserRequest adminUserRequestDTO, @MappingTarget User user);

    AdminUserResponseDTO toDto(User user);
}
