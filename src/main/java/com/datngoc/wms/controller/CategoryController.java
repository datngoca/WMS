package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ApiResponseDTO<List<CategoryResponseDTO>> getCategoryTree() {
        return ApiResponseDTO.<List<CategoryResponseDTO>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(SuccessCode.GET_SUCCESS.getMessageKey())
                .data(categoryService.getCategoryTree())
                .build();
    }

    @PostMapping
    public ApiResponseDTO<Void> createCategory(@RequestBody CategoryRequestDTO categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(SuccessCode.CREATE_SUCCESS.getMessageKey())
                .build();
    }

    @PutMapping("/{id}/move")
    public ApiResponseDTO<Void> moveCategory(@PathVariable Long id, @RequestBody Long newParentId) {
        categoryService.moveCategory(id, newParentId);
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(SuccessCode.UPDATE_SUCCESS.getMessageKey())
                .build();
    }
}
