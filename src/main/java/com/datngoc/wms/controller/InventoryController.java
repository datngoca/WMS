package com.datngoc.wms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.InventoryRequestDTO;
import com.datngoc.wms.service.InventoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Các API liên quan inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    // API Nhập kho
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addStock(@Valid @RequestBody InventoryRequestDTO request) {
        inventoryService.addStock(request.getProductId(), request.getWarehouseId(), request.getQuantity(),
                request.getReason());
        return ResponseEntity.ok("Nhập kho thành công!");
    }

    // API Xuất kho
    @PostMapping("/remove")
    public ResponseEntity<String> removeStock(@Valid @RequestBody InventoryRequestDTO request) {
        inventoryService.removeStock(request.getProductId(), request.getWarehouseId(), request.getQuantity(),
                request.getReason());
        return ResponseEntity.ok("Xuất kho thành công");
    }
}
