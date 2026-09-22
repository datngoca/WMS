package com.datngoc.wms.dto.request;

import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import com.datngoc.wms.dto.common.ProductOptionDTO;
import com.datngoc.wms.entity.json.ProductDetailedSpec;
import com.datngoc.wms.entity.json.ProductSpec;

@Data
public class ProductRequestDTO {
    @Schema(description = "Mã SKU sản phẩm (để trống nếu muốn tự sinh theo định dạng SP-{slug}-00001)")
    private String sku;

    @Schema(description = "Tên sản phẩm")
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @Schema(description = "Danh mục sản phẩm")
    @NotEmpty(message = "Danh mục không được để trống")
    private Set<Long> categories;

    @Schema(description = "Mô tả sản phẩm")
    private String description;

    @Schema(description = "Ảnh đại diện sản phẩm")
    private String imageUrl;

    @Schema(description = "Đơn vị sản phẩm")
    private List<ProductUnitRequestDTO> productUnits;

    @Schema(description = "Thông số nổi bật")
    private List<ProductSpec> specs;

    @Schema(description = "Thông số kỹ thuật chi tiết")
    private List<ProductDetailedSpec> detailedSpecs;

    @Schema(description = "Các tùy chọn sản phẩm")
    private List<ProductOptionDTO> options;
}
