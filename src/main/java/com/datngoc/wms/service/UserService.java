package com.datngoc.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.AdminUserRequest;
import com.datngoc.wms.dto.response.AdminUserResponseDTO;
import com.datngoc.wms.entity.User;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
import com.datngoc.wms.mapper.UserMapper;
import com.datngoc.wms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // 1. Tạo mới người dùng (Admin)
    public AdminUserResponseDTO createUser(AdminUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS, request.getUsername());
        }
        User user = userMapper.toEntity(request);
        return userMapper.toDto(userRepository.save(user));
    }

    // 2. Cập nhật thông tin người dùng (Admin)
    public AdminUserResponseDTO updateUser(Long id, AdminUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateEntityFromDTO(request, existingUser);
        return userMapper.toDto(userRepository.save(existingUser));
    }

    // 3. Xóa người dùng (Admin)
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(existingUser);
    }

    // 4. Lấy thông tin người dùng (Admin)
    public AdminUserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toDto(user);
    }

    // 5. Lấy danh sách tất cả người dùng (Admin)
    public List<AdminUserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
}

