package com.net.usermanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "unit")
@Data
public class UnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "unit_name",nullable = false)
    private String unitName;

    @Column(name = "unit_code", length = 50, nullable = false)
    private String unitCode;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updateDate;

    @Column(name = "update_by")
    private String updateBy;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<UserEntity> users;



}
