package com.datngoc.wms.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.OrderRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.dto.response.OrderResponseDTO;
import com.datngoc.wms.entity.Order;
import com.datngoc.wms.exception.SuccessCode;
import com.datngoc.wms.mapper.OrderMapper;
import com.datngoc.wms.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Các API liên quan đơn hàng / lịch sử bán hàng")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final MessageSource messageSource;

    @Operation(summary = "Tạo đơn hàng (lưu lịch sử bán & trừ kho)", description = "API dùng để tạo đơn bán hàng, tự động trừ tồn kho và ghi nhận lịch sử xuất kho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo đơn hàng thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ hoặc không đủ tồn kho"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @PostMapping
    public ApiResponseDTO<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        Order order = orderService.createOrder(request);
        String msg = messageSource.getMessage(SuccessCode.CREATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<OrderResponseDTO>builder()
                .code(SuccessCode.CREATE_SUCCESS.name())
                .message(msg)
                .data(orderMapper.toDto(order))
                .build();
    }

    @Operation(summary = "Lấy danh sách đơn hàng", description = "API dùng để lấy danh sách lịch sử bán hàng (có phân trang)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    })
    @GetMapping
    public ApiResponseDTO<List<OrderResponseDTO>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderResponseDTO> orderPage = orderService.getAllOrders(page, size);
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<List<OrderResponseDTO>>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(orderPage.getContent())
                .meta(new ApiResponseDTO.Meta(
                        orderPage.getNumber() + 1,
                        orderPage.getSize(),
                        (int) orderPage.getTotalElements()))
                .build();
    }

    @Operation(summary = "Lấy chi tiết đơn hàng", description = "API dùng để lấy chi tiết một đơn hàng theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy chi tiết đơn hàng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @GetMapping("/{id}")
    public ApiResponseDTO<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        OrderResponseDTO order = orderService.getOrderById(id);
        String msg = messageSource.getMessage(SuccessCode.GET_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<OrderResponseDTO>builder()
                .code(SuccessCode.GET_SUCCESS.name())
                .message(msg)
                .data(order)
                .build();
    }

    @Operation(summary = "Hủy đơn hàng", description = "API dùng để hủy đơn hàng và hoàn lại số lượng tồn kho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hủy đơn hàng thành công"),
            @ApiResponse(responseCode = "400", description = "Đơn hàng không thể hủy"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @PutMapping("/{id}/cancel")
    public ApiResponseDTO<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        String msg = messageSource.getMessage(SuccessCode.UPDATE_SUCCESS.getMessageKey(), null,
                LocaleContextHolder.getLocale());

        return ApiResponseDTO.<OrderResponseDTO>builder()
                .code(SuccessCode.UPDATE_SUCCESS.name())
                .message(msg)
                .data(orderMapper.toDto(order))
                .build();
    }
}
