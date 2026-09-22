package com.datngoc.wms.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.datngoc.wms.dto.common.CategoryRef;
import com.datngoc.wms.dto.common.ProductOptionDTO;
import com.datngoc.wms.entity.json.ProductDetailedSpec;
import com.datngoc.wms.entity.json.ProductSpec;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String sku;
    private String name;
    private Set<CategoryRef> categories;
    private String description;
    private String imageUrl;
    private List<ProductSpec> specs;
    private List<ProductDetailedSpec> detailedSpecs;
    private List<ProductOptionDTO> options;
    private List<ProductUnitResponseDTO> productUnits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
