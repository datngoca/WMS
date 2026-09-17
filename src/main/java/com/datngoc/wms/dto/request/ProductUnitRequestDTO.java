package com.datngoc.wms.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductUnitRequestDTO {
    @Schema(description = "Id của bảng Unit ")
    @NotNull(message = "Unit ID không được để trống")
    private Long unitId;

    @Schema(description = "Giá trị quy đổi so với đơn vị cơ sở ")
    @NotNull(message = "Giá trị quy đổi không được để trống")
    private Double exchangeValue;

    @Schema(description = "Giá bán của đơn vị này")
    private BigDecimal price;

    @Schema(description = "Có phải đơn vị cơ sở không ")
    private Boolean isBaseUnit;
}
