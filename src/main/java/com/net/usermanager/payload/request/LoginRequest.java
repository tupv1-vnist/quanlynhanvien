package com.net.usermanager.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String userName;
    private String userPass;

}
