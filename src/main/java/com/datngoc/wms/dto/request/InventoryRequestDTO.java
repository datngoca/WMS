package com.datngoc.wms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {

    @Schema(description = "Id của sản phẩm muốn nhập/xuất" ,example = "1")
    @NotNull(message="Id sản phẩm không được để trống")
    private Long productId;

    @Schema(description = "Id của nhà kho muốn nhập/xuất", example = "1")
    @NotNull(message="Id nhà kho không được để trống")
    private Long warehouseId;

    @Schema(description = "Số lượng sản phẩm muốn nhập/xuất", example="10")
    @Min(value=1, message="Số lượng nhập phải lớn hơn 0")
    private Integer quantity;
    
    private String reason;
}
