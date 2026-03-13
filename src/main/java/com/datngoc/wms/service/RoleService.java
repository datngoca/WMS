package com.datngoc.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.RoleRequestDTO;
import com.datngoc.wms.entity.Role;
import com.datngoc.wms.exception.ResourceNotFoundException;
import com.datngoc.wms.mapper.RoleMapper;
import com.datngoc.wms.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    // 1. Create role
    public Role createRole(RoleRequestDTO request) {
        String formattedName = request.getName().toUpperCase().trim();

        // Kiểm tra nếu chưa có tiền tố ROLE_ thì tự chèn vào
        if (!formattedName.startsWith("ROLE_")) {
            formattedName = "ROLE_" + formattedName;
        }
        request.setName(formattedName);
        Role role = roleMapper.toEntity(request);
        return roleRepository.save(role);
    }

    // 2. Get all role
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    // 3. Get role by id
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role với id: " + id));
    }

    // 4. Update role
    public Role updateRole(Long id, RoleRequestDTO roleRequestDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role với id: " + id));
        roleMapper.updateEntityFromDTO(roleRequestDTO, role);
        return roleRepository.save(role);
    }

    // 5. Delete role
    public void deleteRole(Long id) {
        roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role với id: " + id));
        roleRepository.deleteById(id);
    }
}
