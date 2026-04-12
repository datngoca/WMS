package com.datngoc.wms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.datngoc.wms.dto.request.CategoryRequestDTO;
import com.datngoc.wms.dto.response.CategoryResponseDTO;
import com.datngoc.wms.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    CategoryResponseDTO toDTO(Category category);

    Category toEntity(CategoryRequestDTO categoryRequestDTO);

}
