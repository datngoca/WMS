package com.datngoc.wms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.InventoryRequestDTO;
import com.datngoc.wms.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Các API liên quan đến quản lý tồn kho")
public class InventoryController {

    private final InventoryService inventoryService;

    // API Nhập kho
    @Operation(summary = "Nhập kho", description = "API dùng để tăng số lượng sản phẩm trong một kho cụ thể")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Nhập kho thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
        @ApiResponse(responseCode ="404", description = "Không tìm thấy sản phẩm hoặc kho") 
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addStock(@Valid @RequestBody InventoryRequestDTO request) {
        inventoryService.addStock(request.getProductId(), request.getWarehouseId(), request.getQuantity(),
                request.getReason());
        return ResponseEntity.ok("Nhập kho thành công!");
    }

    // API Xuất kho
    @Operation(summary = "Xuất kho", description = "API dùng để giảm số lượng sản phẩm trong một kho cụ thể")
        @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Nhập kho thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
        @ApiResponse(responseCode ="404", description = "Không tìm thấy sản phẩm hoặc kho") 
    })
    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeStock(@Valid @RequestBody InventoryRequestDTO request) {
        inventoryService.removeStock(request.getProductId(), request.getWarehouseId(), request.getQuantity(),
                request.getReason());
        return ResponseEntity.ok("Xuất kho thành công");
    }
}
