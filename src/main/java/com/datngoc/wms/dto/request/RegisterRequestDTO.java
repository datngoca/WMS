package com.datngoc.wms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @Schema(description = "Tên đăng nhập", example = "user123")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Schema(description = "Mật khẩu", example = "password123")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @Schema(description = "Email", example = "user123@example.com")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Schema(description = "Tên đầy đủ", example = "Nguyễn Văn A")
    @NotBlank(message = "Tên đầy đủ không được để trống")
    private String fullName;
}
