package com.net.usermanager.service;


import com.net.usermanager.entity.RoleEntity;
import com.net.usermanager.enums.ERole;

import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> findByRoleName(ERole roleName);
}
