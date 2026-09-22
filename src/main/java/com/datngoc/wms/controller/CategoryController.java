package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.CategoryRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.dto.response.CategoryResponseDTO;
import com.datngoc.wms.exception.SuccessCode;
import com.datngoc.wms.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Các API liên quan đến quản lý danh mục")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Lấy danh sách danh mục", description = "API dùng để lấy danh sách danh mục dưới dạng cây")
    public ApiResponseDTO<List<CategoryResponseDTO>> getCategoryTree() {
        return ApiResponseDTO.<List<CategoryResponseDTO>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(SuccessCode.GET_SUCCESS.getMessageKey())
                .data(categoryService.getCategoryTree())
                .build();
    }

    @PostMapping
    @Operation(summary = "Tạo danh mục", description = "API dùng để tạo danh mục mới")
    public ApiResponseDTO<Void> createCategory(@RequestBody CategoryRequestDTO categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(SuccessCode.CREATE_SUCCESS.getMessageKey())
                .build();
    }

    @PutMapping("/{id}/move")
    @Operation(summary = "Di chuyển danh mục", description = "API dùng để di chuyển danh mục sang một danh mục cha khác")
    public ApiResponseDTO<Void> moveCategory(@PathVariable Long id, @RequestBody Long newParentId) {
        categoryService.moveCategory(id, newParentId);
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(SuccessCode.UPDATE_SUCCESS.getMessageKey())
                .build();
    }

    @PatchMapping ("/{id}")
    @Operation(summary = "Cập nhật danh mục", description = "API dùng để cập nhật danh mục")
    public ApiResponseDTO<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequest) {
        CategoryResponseDTO updateCategory = categoryService.updateCategory(id, categoryRequest);
        return ApiResponseDTO.<CategoryResponseDTO>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(SuccessCode.UPDATE_SUCCESS.getMessageKey())
                .data(updateCategory)
                .build();
    }

    @PatchMapping("/{id}/open")
    @Operation(summary = "Cập nhật trạng thái mở/đóng danh mục", description = "API dùng để cập nhật trạng thái open hoặc close của danh mục")
    public ApiResponseDTO<Void> updateCategoryOpen(@PathVariable Long id, @RequestBody(required = false) Boolean isOpen) {
        categoryService.updateCategoryOpen(id, isOpen);
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(SuccessCode.UPDATE_SUCCESS.getMessageKey())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy danh mục", description = "API dùng để lấy 1 danh mục")
    public ApiResponseDTO<CategoryResponseDTO> getCategory(@PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ApiResponseDTO.<CategoryResponseDTO>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(SuccessCode.GET_SUCCESS.getMessageKey())
                .data(category)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa danh mục", description = "API dùng để xóa danh mục")
    public ApiResponseDTO<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.DELETE_SUCCESS.name())
                .message(SuccessCode.DELETE_SUCCESS.getMessageKey())
                .build();
    }
}
