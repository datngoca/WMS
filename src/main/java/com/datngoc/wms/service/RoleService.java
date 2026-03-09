package com.datngoc.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.RoleRequestDTO;
import com.datngoc.wms.entity.Role;
import com.datngoc.wms.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;

    public Role createRole(RoleRequestDTO request){
        Role role = new Role();
        role.setName(request.getName());
        return roleRepository.save(role);
    }

    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }
}
