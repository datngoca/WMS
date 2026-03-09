package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.RoleRequestDTO;
import com.datngoc.wms.entity.Role;
import com.datngoc.wms.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Các API liên quan đến Role")
@SecurityRequirement(name = "bearerAuth")

public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Tạo role",description = "Api dùng để tạo vai trò cho hệ thống")
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleRequestDTO request){
        Role response = roleService.createRole(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lấy tất cả role", description = "Api dùng để lấy tất cả role trong hệ thống")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRole(){
        List<Role> response = roleService.getAllRole();
        return ResponseEntity.ok(response);
    }
}
