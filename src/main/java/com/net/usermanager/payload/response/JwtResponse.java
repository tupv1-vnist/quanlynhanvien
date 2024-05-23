package com.net.usermanager.payload.response;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
    private String token;
    private String type="Bearer";
    private String userName;
    private String address;
    private List<String> listRoles;

    public JwtResponse(String token, String userName, String address, List<String> listRoles) {
        this.token = token;
        this.userName = userName;
        this.address = address;
        this.listRoles = listRoles;
    }


}
