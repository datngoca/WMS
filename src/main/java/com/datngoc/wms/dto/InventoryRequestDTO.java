package com.datngoc.wms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {

    @NotNull(message="Id sản phẩm không được để trống")
    private Long productId;

    @NotNull(message="Id nhà kho không được để trống")
    private Long warehouseId;

    @Min(value=1, message="Số lượng nhập phải lớn hơn 0")
    private Integer quantity;
    
    private String reason;
}
