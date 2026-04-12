package com.datngoc.wms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.CategoryRequestDTO;
import com.datngoc.wms.dto.response.CategoryResponseDTO;
import com.datngoc.wms.entity.Category;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
import com.datngoc.wms.mapper.CategoryMapper;
import com.datngoc.wms.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(CategoryRequestDTO categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        if (categoryRequest.getParentId() != null) {
            Category parent = categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParent(parent);
        }
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXISTS, categoryRequest.getName());
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public void moveCategory(Long categoryId, Long newParentId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        Category oldParent = category.getParent();

        Category newParent = categoryRepository.findById(newParentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        // Tránh di chuyển vào chính nó hoặc con của nó (tạo vòng lặp vô tận)
        String currentPrefix = category.getPath() + category.getId() + "/";
        if (category.getId().equals(newParentId) || newParent.getPath().startsWith(currentPrefix)) {
            throw new BusinessException(ErrorCode.CATEGORY_CANNOT_MOVE_TO_CHILD, category.getName());
        }

        String oldPathPrefix = category.getPath() + category.getId() + "/";
        int oldDepth = category.getDepth();

        // Cập nhật node hiện tại
        category.setParent(newParent);
        // Lưu để trigger @PreUpdate cập nhật path và depth mới
        category = categoryRepository.save(category);

        // Cập nhật tất cả con của nó
        String newPathPrefix = category.getPath() + category.getId() + "/";
        int depthDelta = category.getDepth() - oldDepth;

        List<Category> descendants = categoryRepository.findByPathStartingWith(oldPathPrefix);
        for (Category descendant : descendants) {
            descendant.setPath(descendant.getPath().replaceFirst(oldPathPrefix, newPathPrefix));
            descendant.setDepth(descendant.getDepth() + depthDelta);
        }
        categoryRepository.saveAll(descendants);
    }

    public List<CategoryResponseDTO> getCategoryTree() {
        // Get all category from Database
        List<Category> categories = categoryRepository.findAll();

        // Transfer to Map
        Map<Long, CategoryResponseDTO> nodes = categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toMap(CategoryResponseDTO::getId, node -> node));

        List<CategoryResponseDTO> roots = new ArrayList<>();

        // Build tree
        for (CategoryResponseDTO node : nodes.values()) {
            if (node.getParentId() == null) {
                // If not have parent -> root
                roots.add(node);
            } else {
                // If have parent -> add to parent's children
                CategoryResponseDTO parent = nodes.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }
        return roots;
    }
}
