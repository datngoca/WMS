package com.datngoc.wms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoleRequestDTO {
    @Schema(description = "Tên vai trò ", example = "USER")
    private String name;
}
