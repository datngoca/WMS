package com.datngoc.wms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Schema(description ="Tên đăng nhập")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Schema(description = "Mật khẩu")
    @NotBlank(message="Mật khẩu không được để trống")
    @Size(min = 6, message = "Phải có ít nhất 6 ký tự")
    private String password;
}
