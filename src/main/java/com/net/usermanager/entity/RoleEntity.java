package com.net.usermanager.entity;

import javax.persistence.*;

import com.net.usermanager.enums.ERole;
import lombok.Data;

@Entity
@Table(name = "Roles")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleId")
    private int roleId;
    @Column(name = "RoleName")
    @Enumerated(EnumType.STRING)
    private ERole roleName;

}
