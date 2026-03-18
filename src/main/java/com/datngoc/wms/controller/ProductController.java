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

import com.datngoc.wms.dto.request.ProductRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.exception.SuccessCode;
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
    private final MessageSource messageSource;
    private final ProductService productService;

    @Operation(summary = "Tạo sản phẩm", description = "API dùng để tạo sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Tạo sản phẩm thất bại")
    })
    @PostMapping
    public ApiResponseDTO<Product> createProduct(@RequestBody ProductRequestDTO request) {
        Product product = productService.createProduct(request);

        String msg = messageSource.getMessage(SuccessCode.CREATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        return ApiResponseDTO.<Product>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(msg)
                .data(product)
                .build();
    }

    @Operation(summary = "Lấy tất cả sản phẩm", description = "API dùng để lấy tất cả sản phẩm trong DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @GetMapping
    public ApiResponseDTO<List<Product>> getAllProduct() {
        List<Product> products = productService.getAllProducts();
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<List<Product>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(products)
                .build();
    }

    @Operation(summary = "Lấy sản phẩm theo ID", description = "API dùng để lấy sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @GetMapping("/{id}")
    public ApiResponseDTO<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<Product>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(product)
                .build();
    }

    @Operation(summary = "Cập nhật sản phẩm", description = "API dùng để cập nhật sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @PutMapping("/{id}")
    public ApiResponseDTO<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO request) {
        Product product = productService.updateProduct(id, request);
        String msg = messageSource.getMessage(SuccessCode.UPDATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<Product>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(msg)
                .data(product)
                .build();
    }

    @Operation(summary = "Xóa sản phẩm", description = "API dùng để xóa sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @DeleteMapping("/{id}")
    public ApiResponseDTO<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        String msg = messageSource.getMessage(SuccessCode.DELETE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<Void>builder()
                .code(SuccessCode.DELETE_SUCCESS.name())
                .message(msg)
                .build();
    }

}
