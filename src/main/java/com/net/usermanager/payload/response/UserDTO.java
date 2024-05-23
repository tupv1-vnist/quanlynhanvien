package com.net.usermanager.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDTO {

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

}

