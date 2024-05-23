package com.net.usermanager.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.net.usermanager.entity.UnitEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Set;
@Data
@AllArgsConstructor
public class AddUserRequest {
    private Long userId;

    private String userName;

    private String userPass;

    private String fullName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    private String address;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    private String createBy;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updateDate;

    private String updateBy;

    private Long unitId;

    private Long userStatus;

    private Set<String> listRoles;


}
