package com.datngoc.wms.entity.json;

import lombok.Data;
import java.util.List;

@Data
public class ProductDetailedSpec {
    private String groupName;
    private List<ProductSpec> items;
}
