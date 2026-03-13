package com.datngoc.wms.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.datngoc.wms.entity.Role;
import com.datngoc.wms.entity.User;
import com.datngoc.wms.repository.RoleRepository;
import com.datngoc.wms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRespository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Tạo Role nếu chưa có
        Role role = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("ROLE_ADMIN");
            return roleRepository.save(newRole);
        });

        roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("ROLE_USER");
            return roleRepository.save(newRole);
        });

        // 2. Tạo User Admin nếu chưa có
        userRespository.findByUsername("admin").orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername("admin");
            newUser.setRoles(Set.of(role));
            newUser.setActive(true);
            // 3. Mã hóa mật khẩu: passwordEncoder.encode("admin123")
            newUser.setPassword(passwordEncoder.encode("admin123"));
            return userRespository.save(newUser);
        });

        System.out.println("--- Khởi tạo dữ liệu thành công! ---");
    }

}
