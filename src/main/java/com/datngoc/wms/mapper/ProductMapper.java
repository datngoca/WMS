package com.datngoc.wms.mapper;

import org.mapstruct.*;

import com.datngoc.wms.dto.common.ParentCategory;
import com.datngoc.wms.dto.request.ProductRequestDTO;
import com.datngoc.wms.dto.response.ProductResponseDTO;
import com.datngoc.wms.entity.Category;
import com.datngoc.wms.entity.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoryToEntity")
    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoryToDto")
    ProductResponseDTO toDto(Product product);

    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoryToEntity")
    void updateEntityFromDTO(ProductRequestDTO dto, @MappingTarget Product entity);

    // ProductResponseDTO toDto(Product product);

    @Named("mapCategoryToDto")
    default ParentCategory mapCategory(Category category) {
        if (category == null) {
            return null;
        }
        ParentCategory parentCategory = new ParentCategory();
        parentCategory.setId(category.getId());
        parentCategory.setName(category.getName());
        return parentCategory;
    }

    @Named("mapCategoryToEntity")
    default Category mapCategory(ParentCategory parentCategory) {
        if (parentCategory == null) {
            return null;
        }
        Category category = new Category();
        category.setId(parentCategory.getId());
        return category;
    }
    @AfterMapping
    default void linkProductOptions(@MappingTarget Product product) {
        if (product.getOptions() != null) {
            product.getOptions().forEach(option -> {
                option.setProduct(product);
                if (option.getValues() != null) {
                    option.getValues().forEach(value -> value.setOption(option));
                }
            });
        }
    }
}
