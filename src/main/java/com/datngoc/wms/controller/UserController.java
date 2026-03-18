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

import com.datngoc.wms.dto.request.AdminUserRequest;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.entity.User;
import com.datngoc.wms.exception.SuccessCode;
import com.datngoc.wms.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Các API liên quan đến User")
public class UserController {
    private final MessageSource messageSource;
    private final UserService userService;

    @Operation(summary = "Tạo người dùng mới", description = "API dùng để tạo người dùng mới (Admin)")
    @PostMapping
    public ApiResponseDTO<User> createUser(@RequestBody AdminUserRequest request) {
        User createdUser = userService.createUser(request);

        String msg = messageSource.getMessage(SuccessCode.CREATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<User>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(msg)
                .data(createdUser)
                .build();
    }

    @Operation(summary = "Cập nhật thông tin người dùng", description = "API dùng để cập nhật thông tin người dùng (Admin)")
    @PutMapping("/{id}")
    public ApiResponseDTO<User> updateUser(@PathVariable("id") Long id, @RequestBody AdminUserRequest request) {
        User updatedUser = userService.updateUser(id, request);
        String msg = messageSource.getMessage(SuccessCode.UPDATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<User>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(msg)
                .data(updatedUser)
                .build();
    }

    @Operation(summary = "Xóa người dùng", description = "API dùng để xóa người dùng (Admin)")
    @DeleteMapping("/{id}")
    public ApiResponseDTO<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        String msg = messageSource.getMessage(SuccessCode.DELETE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.DELETE_SUCCESS.name())
                .message(msg)
                .build();
    }

    @Operation(summary = "Lấy thông tin người dùng theo ID", description = "API dùng để lấy thông tin người dùng theo ID (Admin)")
    @GetMapping("/{id}")
    public ApiResponseDTO<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<User>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(user)
                .build();
    }

    @Operation(summary = "Lấy danh sách tất cả người dùng", description = "API dùng để lấy danh sách tất cả người dùng (Admin)")
    @GetMapping
    public ApiResponseDTO<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<List<User>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(users)
                .build();
    }
}
