package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.WarehouseRequestDTO;
import com.datngoc.wms.entity.Warehouse;
import com.datngoc.wms.service.WarehouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/warehouses")
@Tag(name = "Warehouse", description = "Các API liên quan đến Warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @Operation(summary = "Tạo nhà kho mới", description = "API dùng để tạo nhà kho mới")
    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody WarehouseRequestDTO request) {
        Warehouse response = warehouseService.createWarehouse(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lấy tất cả thông tin nhà kho", description = "API dùng để lấy tất cả nhà kho trong DB")
    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouse() {
        List<Warehouse> response = warehouseService.getAllWarehouse();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lấy thông tin nhà kho theo ID", description = "API dùng để lấy thông tin nhà kho theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable("id") Long id) {
        Warehouse response = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cập nhật thông tin nhà kho", description = "API dùng để cập nhật thông tin nhà kho theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable("id") Long id, @RequestBody WarehouseRequestDTO request) {
        Warehouse response = warehouseService.updateWarehouse(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Xóa nhà kho", description = "API dùng để xóa nhà kho theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable("id") Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }
}
