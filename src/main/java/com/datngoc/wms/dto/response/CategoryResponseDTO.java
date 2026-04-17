package com.datngoc.wms.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.datngoc.wms.dto.common.ParentCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private ParentCategory parent;
    private Integer depth;
    private List<CategoryResponseDTO> children = new ArrayList<>();

}
