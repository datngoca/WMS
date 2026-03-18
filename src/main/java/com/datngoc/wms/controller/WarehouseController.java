package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.WarehouseRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.entity.Warehouse;
import com.datngoc.wms.exception.SuccessCode;
import com.datngoc.wms.service.WarehouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/warehouses")
@Tag(name = "Warehouse", description = "Các API liên quan đến Warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final MessageSource messageSource;
    private final WarehouseService warehouseService;

    @Operation(summary = "Tạo nhà kho mới", description = "API dùng để tạo nhà kho mới")
    @PostMapping
    public ApiResponseDTO<Warehouse> createWarehouse(@RequestBody WarehouseRequestDTO request) {
        Warehouse response = warehouseService.createWarehouse(request);
        String msg = messageSource.getMessage(SuccessCode.CREATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Warehouse>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(msg)
                .data(response)
                .build();
    }

    @Operation(summary = "Lấy tất cả thông tin nhà kho", description = "API dùng để lấy tất cả nhà kho trong DB")
    @GetMapping
    public ApiResponseDTO<List<Warehouse>> getAllWarehouse() {
        List<Warehouse> response = warehouseService.getAllWarehouse();
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<List<Warehouse>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(response)
                .build();
    }

    @Operation(summary = "Lấy thông tin nhà kho theo ID", description = "API dùng để lấy thông tin nhà kho theo ID")
    @GetMapping("/{id}")
    public ApiResponseDTO<Warehouse> getWarehouseById(@PathVariable("id") Long id) {
        Warehouse response = warehouseService.getWarehouseById(id);
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Warehouse>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(response)
                .build();
    }

    @Operation(summary = "Cập nhật thông tin nhà kho", description = "API dùng để cập nhật thông tin nhà kho theo ID")
    @PutMapping("/{id}")
    public ApiResponseDTO<Warehouse> updateWarehouse(@PathVariable("id") Long id,
            @RequestBody WarehouseRequestDTO request) {
        Warehouse response = warehouseService.updateWarehouse(id, request);
        String msg = messageSource.getMessage(SuccessCode.UPDATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Warehouse>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(msg)
                .data(response)
                .build();
    }

    @Operation(summary = "Xóa nhà kho", description = "API dùng để xóa nhà kho theo ID")
    @DeleteMapping("/{id}")
    public ApiResponseDTO<Void> deleteWarehouse(@PathVariable("id") Long id) {
        warehouseService.deleteWarehouse(id);
        String msg = messageSource.getMessage(SuccessCode.DELETE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.DELETE_SUCCESS.name())
                .message(msg)
                .build();
    }
}
