package com.datngoc.wms.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.datngoc.wms.dto.common.ParentCategory;
import com.datngoc.wms.dto.common.ProductOptionDTO;
import com.datngoc.wms.entity.ProductOption;
import com.datngoc.wms.entity.json.ProductDetailedSpec;
import com.datngoc.wms.entity.json.ProductSpec;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String sku;
    private String name;
    private Set<ParentCategory> categories;
    private String description;
    private BigDecimal basePrice;
    private List<ProductSpec> specs;
    private List<ProductDetailedSpec> detailedSpecs;
    private List<ProductOptionDTO> options;
}
