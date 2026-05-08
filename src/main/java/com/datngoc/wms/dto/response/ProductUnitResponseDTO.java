package com.datngoc.wms.dto.response;

import java.math.BigDecimal;
import lombok.Data;
import com.datngoc.wms.entity.Unit;

@Data
public class ProductUnitResponseDTO {
    private Long id; // ID của dòng ProductUnit
    private Unit unit; // Chứa thông tin gốc của Unit (id, name, code) để UI hiển thị
    private Double exchangeValue;
    private BigDecimal price;
    private Boolean isBaseUnit;
}
