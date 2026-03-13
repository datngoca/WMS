package com.datngoc.wms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.AdminUserRequest;
import com.datngoc.wms.entity.User;
import com.datngoc.wms.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Các API liên quan đến User")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Tạo người dùng mới", description = "API dùng để tạo người dùng mới (Admin)")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody AdminUserRequest request) {
        User createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Cập nhật thông tin người dùng", description = "API dùng để cập nhật thông tin người dùng (Admin)")
    @PostMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody AdminUserRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Xóa người dùng", description = "API dùng để xóa người dùng (Admin)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lấy thông tin người dùng theo ID", description = "API dùng để lấy thông tin người dùng theo ID (Admin)")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Lấy danh sách tất cả người dùng", description = "API dùng để lấy danh sách tất cả người dùng (Admin)")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
