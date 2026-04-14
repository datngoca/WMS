package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import com.datngoc.wms.dto.request.UnitRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.entity.Unit;
import com.datngoc.wms.exception.SuccessCode;
import com.datngoc.wms.service.UnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
@Tag(name = "Units", description = "Quản lý đơn vị tính")
public class UnitController {

    private final UnitService unitService;
    private final MessageSource messageSource;

    @PostMapping
    @Operation(summary = "Tạo đơn vị tính mới", description = "Tạo đơn vị tính mới")
    public ApiResponseDTO<Unit> createUnit(@Valid @RequestBody UnitRequestDTO unitRequest) {
        Unit createdUnit = unitService.createUnit(unitRequest);
        String msg = messageSource.getMessage(SuccessCode.CREATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Unit>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(msg)
                .data(createdUnit)
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách đơn vị tính", description = "Lấy danh sách đơn vị tính")
    public ApiResponseDTO<List<Unit>> getAllUnits() {
        List<Unit> units = unitService.getAllUnits();
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<List<Unit>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(units)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật đơn vị tính", description = "Cập nhật đơn vị tính")
    public ApiResponseDTO<Unit> updateUnit(@PathVariable Long id, @Valid @RequestBody UnitRequestDTO unitRequest) {
        Unit updatedUnit = unitService.updateUnit(id, unitRequest);
        String msg = messageSource.getMessage(SuccessCode.UPDATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Unit>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(msg)
                .data(updatedUnit)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa đơn vị tính", description = "Xóa đơn vị tính")
    public ApiResponseDTO<Void> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        String msg = messageSource.getMessage(SuccessCode.DELETE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.DELETE_SUCCESS.name())
                .message(msg)
                .build();
    }
}
