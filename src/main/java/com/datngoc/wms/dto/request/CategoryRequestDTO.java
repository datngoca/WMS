package com.datngoc.wms.dto.request;

import com.datngoc.wms.dto.common.ParentCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {
    private String name;
    private String description;
    private ParentCategory parent;
}
