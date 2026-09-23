package com.datngoc.wms.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @Schema(description = "Tên khách hàng", example = "Nguyễn Văn A")
    private String customerName;

    @Schema(description = "Số điện thoại khách hàng", example = "0987654321")
    private String customerPhone;

    @Schema(description = "Ghi chú đơn hàng", example = "Giao hàng giờ hành chính")
    private String note;

    @Schema(description = "Danh sách chi tiết sản phẩm cần mua")
    @NotEmpty(message = "Danh sách sản phẩm không được để trống")
    @Valid
    private List<OrderItemRequestDTO> items;
}
