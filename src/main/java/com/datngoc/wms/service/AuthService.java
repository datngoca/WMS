package com.datngoc.wms.service;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.LoginRequestDTO;
import com.datngoc.wms.dto.request.RegisterRequestDTO;
import com.datngoc.wms.dto.response.LoginResponseDTO;
import com.datngoc.wms.dto.response.RegisterResponseDTO;
import com.datngoc.wms.entity.Role;
import com.datngoc.wms.entity.User;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
import com.datngoc.wms.mapper.RegisterMapper;
import com.datngoc.wms.repository.RoleRepository;
import com.datngoc.wms.repository.UserRepository;
// import com.datngoc.wms.repository.UserRespository;
import com.datngoc.wms.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRespository;

    private final RegisterMapper registerMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        Boolean isExist = userRespository.findByUsername(registerRequestDTO.getUsername()).isPresent();
        if (isExist) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        }
        Role roleUser =  roleRepository.findByName("ROLE_USER").orElseThrow(()-> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
        User user = registerMapper.toEntity(registerRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleUser));
        return registerMapper.toDto(userRespository.save(user));
    }
}
