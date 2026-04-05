package com.datngoc.wms.entity.json;

import lombok.Data;

@Data
public class ProductSpec {
    private String label;
    private Object value; // Có thể lưu chuỗi ("6.7\"") hoặc số (6)
}
