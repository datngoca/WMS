package com.datngoc.wms.dto.response;

import java.util.Set;

import com.datngoc.wms.entity.Role;

import lombok.Data;

@Data
public class AdminUserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Boolean active;
    private Set<Role> roles;
}
