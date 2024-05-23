package com.net.usermanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name",unique = true,nullable = false)
    private String userName;

    @Column(name = "user_pass",nullable = false)
    private String userPass;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name="date_of_birth",nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "created_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "status")
    private Long status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserRole",joinColumns = @JoinColumn(name = "UserId"),
               inverseJoinColumns = @JoinColumn(name = "RoleId"))

    private Set<RoleEntity> listRoles=new HashSet<>();


}
