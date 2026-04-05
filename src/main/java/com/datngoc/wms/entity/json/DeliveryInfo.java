package com.datngoc.wms.entity.json;

import lombok.Data;

@Data
public class DeliveryInfo {
    private String freeDelivery;
    private String inStock;
    private String guaranteed;
}
