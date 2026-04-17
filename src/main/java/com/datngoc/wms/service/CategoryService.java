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
        if (categoryRequest.getParent() != null) {
            Category parent = categoryRepository.findById(categoryRequest.getParent().getId())
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

        Category newParent = categoryRepository.findById(newParentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        // Tránh di chuyển vào chính nó hoặc con của nó (tạo vòng lặp vô tận)
        String currentPrefix = category.getPath() + category.getId() + "/";
        if (category.getId().equals(newParentId) || newParent.getPath().startsWith(currentPrefix)) {
            throw new BusinessException(ErrorCode.CATEGORY_CANNOT_MOVE_TO_CHILD, category.getName());
        }

        updateCategoryHierarchy(category, newParent);
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
            if (node.getParent() == null || node.getParent().getId() == null) {
                // If not have parent -> root
                roots.add(node);
            } else {
                // If have parent -> add to parent's children
                CategoryResponseDTO parentNode = nodes.get(node.getParent().getId());
                if (parentNode != null) {
                    parentNode.getChildren().add(node);
                }
            }
        }
        return roots;
    }

    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        // 1. Cập nhật name và description (Có check trùng tên)
        if (!category.getName().equals(categoryRequest.getName())
                && categoryRepository.existsByName(categoryRequest.getName())) {
            throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXISTS, categoryRequest.getName());
        }
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        // 2. Nếu đổi danh mục cha
        Long currentParentId = category.getParent() != null ? category.getParent().getId() : null;
        Long newParentId = categoryRequest.getParent().getId();

        category = categoryRepository.save(category); // Lưu lại thông tin cơ bản trước

        if (newParentId != null && !newParentId.equals(currentParentId)) {
            // Gọi uỷ quyền qua hàm moveCategory để kiểm tra lỗi và tái sử dụng toàn bộ
            // logic
            moveCategory(id, newParentId);
            category = categoryRepository.findById(id).get(); // Reload từ DB
        } else if (newParentId == null && currentParentId != null) {
            // Gỡ parent (Chuyển ra gốc)
            updateCategoryHierarchy(category, null);
        }

        return categoryMapper.toDTO(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
        if (categoryRepository.existsByParentId(id)) {
            throw new BusinessException(ErrorCode.CATEGORY_CANNOT_DELETE_WITH_CHILD, category.getName());
        }
        categoryRepository.delete(category);
    }

    /**
     * Hàm chuẩn hoá đệ quy Cây phân cấp
     * Xử lý lại Path và Depth cho node hiện tại và tất cả các node con của NÓ
     * khi Node hiện tại chuyển sang làm con của một NewParent (hoặc ra Root nếu
     * truyền null).
     */
    private void updateCategoryHierarchy(Category category, Category newParent) {
        String oldPathPrefix = category.getPath() + category.getId() + "/";
        int oldDepth = category.getDepth();

        // 1. Cập nhật node hiện tại
        category.setParent(newParent);
        // Lưu để trigger @PreUpdate cập nhật lại Path và Depth mới cho NÓ
        category = categoryRepository.save(category);

        // 2. Cập nhật tất cả con của nó
        String newPathPrefix = category.getPath() + category.getId() + "/";
        int depthDelta = category.getDepth() - oldDepth;

        List<Category> descendants = categoryRepository.findByPathStartingWith(oldPathPrefix);
        for (Category descendant : descendants) {
            descendant.setPath(descendant.getPath().replaceFirst(oldPathPrefix, newPathPrefix));
            descendant.setDepth(descendant.getDepth() + depthDelta);
        }

        if (!descendants.isEmpty()) {
            categoryRepository.saveAll(descendants);
        }
    }
}
