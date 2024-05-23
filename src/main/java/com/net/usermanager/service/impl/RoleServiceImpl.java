package com.net.usermanager.service.impl;



import com.net.usermanager.entity.RoleEntity;
import com.net.usermanager.enums.ERole;
import com.net.usermanager.repository.RoleRepository;
import com.net.usermanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<RoleEntity> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
