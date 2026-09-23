package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.InventoryRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.dto.response.InventoryResponseDTO;
import com.datngoc.wms.exception.SuccessCode;
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
        private final MessageSource messageSource;
        private final InventoryService inventoryService;

        // API Lấy danh sách tồn kho
        @Operation(summary = "Lấy danh sách tồn kho", description = "API dùng để lấy danh sách số lượng tồn kho theo sản phẩm (phân trang)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lấy danh sách tồn kho thành công")
        })
        @GetMapping
        public ApiResponseDTO<List<InventoryResponseDTO>> getAllInventories(
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size) {
                Page<InventoryResponseDTO> invPage = inventoryService.getAllInventories(page, size);
                String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                                LocaleContextHolder.getLocale());

                return ApiResponseDTO.<List<InventoryResponseDTO>>builder()
                                .code(SuccessCode.GET_SUCCESS.name())
                                .message(msg)
                                .data(invPage.getContent())
                                .meta(new ApiResponseDTO.Meta(
                                                invPage.getNumber() + 1,
                                                invPage.getSize(),
                                                (int) invPage.getTotalElements()))
                                .build();
        }

        // API Nhập kho
        @Operation(summary = "Nhập kho", description = "API dùng để tăng số lượng sản phẩm trong kho")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Nhập kho thành công"),
                        @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
                        @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
        })
        @PostMapping("/add")
        public ApiResponseDTO<Void> addStock(@Valid @RequestBody InventoryRequestDTO request) {
                inventoryService.addStock(request.getProductId(), request.getQuantity(),
                                request.getReason());
                String msg = messageSource.getMessage(SuccessCode.IMPORT_SUCCESS.getMessageKey(), null,
                                LocaleContextHolder.getLocale());

                return ApiResponseDTO.<Void>builder()
                                .code(SuccessCode.IMPORT_SUCCESS.name())
                                .message(msg)
                                .build();
        }

        // API Xuất kho
        @Operation(summary = "Xuất kho", description = "API dùng để giảm số lượng sản phẩm trong kho")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Xuất kho thành công"),
                        @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
                        @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
        })
        @PostMapping("/remove")
        public ApiResponseDTO<Void> removeStock(@Valid @RequestBody InventoryRequestDTO request) {
                inventoryService.removeStock(request.getProductId(), request.getQuantity(),
                                request.getReason());
                String msg = messageSource.getMessage(SuccessCode.EXPORT_SUCCESS.getMessageKey(), null,
                                LocaleContextHolder.getLocale());

                return ApiResponseDTO.<Void>builder()
                                .code(SuccessCode.EXPORT_SUCCESS.name())
                                .message(msg)
                                .build();
        }
}
