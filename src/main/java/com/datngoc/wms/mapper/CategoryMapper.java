package com.datngoc.wms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.datngoc.wms.dto.request.CategoryRequestDTO;
import com.datngoc.wms.dto.response.CategoryResponseDTO;
import com.datngoc.wms.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    // ENTITY → DTO
    @Mapping(target = "parent", source = "parent", qualifiedByName = "mapParent")
    CategoryResponseDTO toDTO(Category category);

    // DTO → ENTITY
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapParentId")
    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    // -------- CUSTOM --------

    @Named("mapParentId")
    default Category mapParent(Long parentId) {
        if (parentId == null)
            return null;

        Category parent = new Category();
        parent.setId(parentId); // chỉ cần set id
        return parent;
    }

    @Named("mapParent")
    default CategoryResponseDTO.ParentCategory mapParent(Category parent) {
        if (parent == null)
            return null;

        return new CategoryResponseDTO.ParentCategory(
                parent.getId(),
                parent.getName());
    }

}
