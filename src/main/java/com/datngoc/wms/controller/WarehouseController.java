package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.entity.Warehouse;
import com.datngoc.wms.service.WarehouseService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/warehouse")
@Tag(name = "Warehouse", description = "Các Api liên quan đến Warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse request) {
        Warehouse response = warehouseService.createWarehouse(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouse(){
        List<Warehouse> response = warehouseService.getAllWarehouse();
        return ResponseEntity.ok(response);
    }
}
