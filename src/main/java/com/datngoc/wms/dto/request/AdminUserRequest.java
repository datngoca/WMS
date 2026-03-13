package com.datngoc.wms.dto.request;

import java.util.Set;

import com.datngoc.wms.entity.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserRequest extends RegisterRequestDTO {
    private Set<Role> roles;
}
