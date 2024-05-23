package com.net.usermanager.security;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.net.usermanager.entity.UnitEntity;
import com.net.usermanager.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long userId;

    private String userName;
    @JsonIgnore
    private String userPass;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    private String address;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    private String createBy;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updateDate;
    private String updateBy;

    private Long status;


    private UnitEntity unit;

    public Collection<? extends GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    // Từ thông tin user chuyen sang thông tin CustomUserDetails
    public static CustomUserDetails mapUserToUserDetail(UserEntity user){
        // Lấy các quyền t đối tượng user
        List<GrantedAuthority> listAuthorities=user.getListRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                .collect(Collectors.toList());
        // Trả về đối tượng CustomUserDetails
        return new CustomUserDetails(
                user.getUserId(),
                user.getUserName(),
                user.getUserPass(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getCreatedDate(),
                user.getCreateBy(),
                user.getUpdateDate(),
                user.getUpdateBy(),
                user.getStatus(),
                user.getUnit(),
                listAuthorities
        );
    }

    @Override
    public String getPassword() {
        return this.userPass;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
