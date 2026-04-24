package com.datngoc.wms.dto.common;

import java.util.List;

import lombok.Data;

@Data
public class ProductOptionDTO {
    private String name;
    private String type;
    private List<ProductOptionValueDTO> values;
}
