package com.datngoc.wms.dto;

import lombok.Data;

@Data
public class InventoryRequestDTO {
    private Long productId;
    private Long warehouseId;
    private Integer quantity;
    private String reason;
}
