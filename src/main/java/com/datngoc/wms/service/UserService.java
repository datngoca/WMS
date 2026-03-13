package com.datngoc.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.AdminUserRequest;
import com.datngoc.wms.entity.User;
import com.datngoc.wms.exception.ResourceNotFoundException;
import com.datngoc.wms.mapper.UserMapper;
import com.datngoc.wms.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    // 1. Tạo mới người dùng (Admin)
    public User createUser(AdminUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Người dùng đã tồn tại với username: " + request.getUsername());
        }
        User user = userMapper.toEntity(request);
        return userRepository.save(user);
    }

    // 2. Cập nhật thông tin người dùng (Admin)
    public User updateUser(Long id, AdminUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));
        userMapper.updateEntityFromDTO(request, existingUser);
        return userRepository.save(existingUser);
    }

    // 3. Xóa người dùng (Admin)
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));
        userRepository.delete(existingUser);
    }

    // 4. Lấy thông tin người dùng (Admin)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));
    }

    // 5. Lấy danh sách tất cả người dùng (Admin)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
