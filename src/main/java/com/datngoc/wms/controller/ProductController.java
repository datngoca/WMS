package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.ProductRequestDTO;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Các API liên quan product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Tạo sản phẩm", description = "API dùng để tạo sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Tạo sản phẩm thất bại")
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Lấy tất cả sản phẩm", description = "API dùng để lấy tất cả sản phẩm trong DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> product = productService.getAllProducts();
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Lấy sản phẩm theo ID", description = "API dùng để lấy sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Cập nhật sản phẩm", description = "API dùng để cập nhật sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @PostMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO request) {
        Product product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Xóa sản phẩm", description = "API dùng để xóa sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
