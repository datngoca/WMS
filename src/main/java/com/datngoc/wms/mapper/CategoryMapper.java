package com.datngoc.wms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.datngoc.wms.dto.common.CategoryRef;
import com.datngoc.wms.dto.request.CategoryRequestDTO;
import com.datngoc.wms.dto.response.CategoryResponseDTO;
import com.datngoc.wms.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    // ENTITY → DTO
    @Mapping(target = "parent", source = "parent", qualifiedByName = "mapParentToDTO")
    CategoryResponseDTO toDTO(Category category);

    // DTO → ENTITY
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapParentToEntity")
    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    // -------- CUSTOM --------

    @Named("mapParentToDTO")
    default CategoryRef mapParentToDTO(Category parent) {
        if (parent == null)
            return null;

        CategoryRef categoryRef = new CategoryRef();
        categoryRef.setId(parent.getId());
        categoryRef.setName(parent.getName());
        return categoryRef;
    }

    @Named("mapParentToEntity")
    default Category mapParentToEntity(Long parentId) {
        if (parentId == null)
            return null;

        Category category = new Category();
        category.setId(parentId);
        return category;
    }

}
