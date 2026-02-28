package com.datngoc.wms.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String sku;
    private String name;
    private String category;
    private BigDecimal basePrice;
}
