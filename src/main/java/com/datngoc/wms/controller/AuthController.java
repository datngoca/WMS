package com.datngoc.wms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.LoginRequestDTO;
import com.datngoc.wms.dto.request.RegisterRequestDTO;
import com.datngoc.wms.dto.response.ApiResponseDTO;
import com.datngoc.wms.dto.response.LoginResponseDTO;
import com.datngoc.wms.dto.response.RegisterResponseDTO;
import com.datngoc.wms.exception.SuccessCode;
import com.datngoc.wms.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Các API liên quan đến authentication")
public class AuthController {
    private final MessageSource messageSource;

    private final AuthService authService;

    @Operation(summary = "Đăng nhập", description = "API dùng để lấy token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @PostMapping("/login")
    public ApiResponseDTO<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO responseDTO = authService.login(request);

        Object[] args = { responseDTO.getUsername() };

        String msg = messageSource.getMessage(SuccessCode.AUTH_SUCCESS.getMessageKey(), args,
                LocaleContextHolder.getLocale());

        ApiResponseDTO<LoginResponseDTO> response = ApiResponseDTO.<LoginResponseDTO>builder()
                .code(SuccessCode.AUTH_SUCCESS.name())
                .message(msg)
                .data(responseDTO)
                .build();
        return response;
    }

    @Operation(summary = "Đăng ký", description = "API dùng để đăng ký tài khoản mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng ký thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Đăng ký thất bại")
    })
    @PostMapping("/register")
    public ApiResponseDTO<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO responseDTO = authService.register(registerRequestDTO);

        Object[] args = { responseDTO.getUsername() };

        String msg = messageSource.getMessage(SuccessCode.REGISTER_SUCCESS.getMessageKey(), args,
                LocaleContextHolder.getLocale());

        ApiResponseDTO<RegisterResponseDTO> response = ApiResponseDTO.<RegisterResponseDTO>builder()
                .code(SuccessCode.REGISTER_SUCCESS.name())
                .message(msg)
                .data(responseDTO)
                .build();
        return response;

    }

}
