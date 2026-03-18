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

import com.datngoc.wms.dto.request.RoleRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.entity.Role;
import com.datngoc.wms.exception.SuccessCode;
import com.datngoc.wms.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Các API liên quan đến Role")
@SecurityRequirement(name = "bearerAuth")

public class RoleController {
    private final MessageSource messageSource;
    private final RoleService roleService;

    @Operation(summary = "Tạo role", description = "Api dùng để tạo vai trò cho hệ thống")
    @PostMapping
    public ApiResponseDTO<Role> createRole(@RequestBody RoleRequestDTO request) {
        Role response = roleService.createRole(request);

        String msg = messageSource.getMessage(SuccessCode.CREATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Role>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(msg)
                .data(response)
                .build();
    }

    @Operation(summary = "Lấy tất cả role", description = "Api dùng để lấy tất cả role trong hệ thống")
    @GetMapping
    public ApiResponseDTO<List<Role>> getAllRole() {
        List<Role> roles = roleService.getAllRole();
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null, "ko thay key",
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<List<Role>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(roles)
                .build();
    }

    @Operation(summary = "Lấy role theo ID", description = "Api dùng để lấy role theo ID")
    @GetMapping("/{id}")
    public ApiResponseDTO<Role> getRoleById(@PathVariable("id") Long id) {
        Role response = roleService.getRoleById(id);
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Role>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(response)
                .build();
    }

    @Operation(summary = "Cập nhật role", description = "Api dùng để cập nhật role theo ID")
    @PutMapping("/{id}")
    public ApiResponseDTO<Role> updateRole(@PathVariable("id") Long id, @RequestBody RoleRequestDTO request) {
        Role response = roleService.updateRole(id, request);
        String msg = messageSource.getMessage(SuccessCode.UPDATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Role>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(msg)
                .data(response)
                .build();
    }

    @Operation(summary = "Xóa role", description = "Api dùng để xóa role theo ID")
    @DeleteMapping("/{id}")
    public ApiResponseDTO<Void> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        String msg = messageSource.getMessage(SuccessCode.DELETE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.DELETE_SUCCESS.name())
                .message(msg)
                .build();
    }
}
