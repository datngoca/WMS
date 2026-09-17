package com.datngoc.wms.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductUnitResponseDTO {
    private UnitResponseDTO unit;
    private String sku;
    private String barcode;
    private Double exchangeValue;
    private BigDecimal price;
    private Boolean isBaseUnit;
}