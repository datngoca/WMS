package com.datngoc.wms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UnitResponseDTO {
    @Schema(description = "Id đơn vị")
    private Long id;

    @Schema(description = "Tên đơn vị")
    private String name;

}
