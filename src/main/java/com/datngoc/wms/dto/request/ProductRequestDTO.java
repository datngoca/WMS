package com.datngoc.wms.dto.request;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.datngoc.wms.entity.json.ProductDetailedSpec;
import com.datngoc.wms.entity.json.ProductSpec;

@Data
public class ProductRequestDTO {
    @Schema(description = "Mã SKU sản phẩm")
    @NotBlank(message="SKU không được để trống")
    private String sku;

    @Schema(description = "Tên sản phẩm")
    @NotBlank(message="Tên sản phẩm không được để trống")
    private String name;

    @Schema(description = "Danh mục sản phẩm")
    @NotBlank(message="Danh mục không được để trống")
    private String category;

    @Schema(description = "Mô tả sản phẩm")
    private String description;

    @Schema(description = "Gía sản phẩm")
    @NotNull(message="Gía không được để trống")
    @Min(value = 1, message = "Gía sản phẩm không được bé hơn 1")
    private BigDecimal basePrice;

    @Schema(description = "Giá gốc sản phẩm")
    private BigDecimal originalPrice;

    @Schema(description = "Thông số nổi bật")
    private List<ProductSpec> specs;

    @Schema(description = "Thông số kỹ thuật chi tiết")
    private List<ProductDetailedSpec> detailedSpecs;
}
