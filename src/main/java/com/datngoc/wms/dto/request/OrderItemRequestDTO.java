package com.datngoc.wms.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDTO {
    @Schema(description = "ID của sản phẩm", example = "1")
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId;

    @Schema(description = "Số lượng mua", example = "2")
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private Integer quantity;

    @Schema(description = "Đơn giá bán", example = "150000")
    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private BigDecimal unitPrice;
}
