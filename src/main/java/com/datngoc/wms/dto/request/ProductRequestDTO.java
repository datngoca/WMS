package com.datngoc.wms.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @Schema(description = "Mã SKU sản phẩm")
    @NotBlank(message="SKU không được để trông")
    private String sku;

    @Schema(description = "Tên sản phẩm")
    @NotBlank(message="Tên sản phẩm không được để trống")
    private String name;

    @Schema(description = "Danh mục sản phẩm")
    @NotBlank(message="Danh mục không được để trống")
    private String category;

    @Schema(description = "Gía sản phẩm")
    @NotNull(message="Gía không được để trống")
    @Min(value = 1, message = "Gía sản phẩm không được bé hơn 1")
    private BigDecimal basePrice;
}
