package com.datngoc.wms.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.LoginRequestDTO;
import com.datngoc.wms.dto.LoginResponseDTO;
// import com.datngoc.wms.repository.UserRespository;
import com.datngoc.wms.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    // private final UserRespository userRespository;

    public LoginResponseDTO login(LoginRequestDTO request) {
        // 1. Xác thực người dùng
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // 2. Nếu không có lỗi thì đặt vào SecurityContext(Optional)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Tạo token
        String jwt = jwtUtils.generateToken(authentication);

        // 4. Trả về kết quả
        return new LoginResponseDTO(jwt, request.getUsername());
    }
}
