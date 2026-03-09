package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.entity.Product;
import com.datngoc.wms.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Các API liên quan product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Tạo sản phẩm", description = "API dùng để tạo sản phẩm")
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Lấy tất cả sản phẩm", description = "API dùng để lấy tất cả sản phẩm trong DB")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> product = productService.getAllProducts();
        return ResponseEntity.ok(product);
    }
}
