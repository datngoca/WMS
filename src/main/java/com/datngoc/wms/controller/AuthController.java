package com.datngoc.wms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datngoc.wms.dto.request.LoginRequestDTO;
import com.datngoc.wms.dto.request.RegisterRequestDTO;
import com.datngoc.wms.dto.response.LoginResponseDTO;
import com.datngoc.wms.dto.response.RegisterResponseDTO;
import com.datngoc.wms.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Các API liên quan đến authentication")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Đăng nhập", description = "API dùng để lấy token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO responseDTO = authService.login(request);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Đăng ký", description = "API dùng để đăng ký tài khoản mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng ký thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu gửi lên không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Đăng ký thất bại")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO responseDTO = authService.register(registerRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

}
