package com.datngoc.wms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WarehouseRequestDTO {

    @Schema(description = "Tên kho hàng", example = "KHO_001")
    @NotBlank(message = "Tên kho hàng không được để trống")
    private String name;

    @Schema(description = "Địa chỉ kho hàng", example = "123 Đường ABC, Quận XYZ, TP. HCM")
    private String address;

    @Schema(description = "Sức chứa tối đa của kho hàng", example = "1000")
    @Min(value = 1, message = "Sức chứa phải lớn hơn 0")
    private Integer capacity;
}
