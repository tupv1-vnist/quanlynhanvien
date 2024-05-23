package com.net.usermanager.controller;

import com.net.usermanager.entity.RoleEntity;
import com.net.usermanager.entity.UserEntity;
import com.net.usermanager.enums.ERole;
import com.net.usermanager.jwt.JwtTokenProvider;
import com.net.usermanager.payload.request.LoginRequest;
import com.net.usermanager.payload.request.AddUserRequest;
import com.net.usermanager.payload.response.JwtResponse;
import com.net.usermanager.payload.response.MessageResponse;
import com.net.usermanager.security.CustomUserDetails;
import com.net.usermanager.service.RoleService;
import com.net.usermanager.service.UnitService;
import com.net.usermanager.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Log4j2
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UnitService unitService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody AddUserRequest addUserRequest) {
        if (userService.existsByUserName(addUserRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already"));
        }
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String createdName=authentication.getName();
        UserEntity user = new UserEntity();
        user.setUserName(addUserRequest.getUserName());
        user.setUserPass(passwordEncoder.encode(addUserRequest.getUserPass()));
        user.setFullName(addUserRequest.getFullName());
        user.setDateOfBirth(addUserRequest.getDateOfBirth());
        user.setAddress(addUserRequest.getAddress());
        user.setCreatedDate(new Date());
        user.setCreateBy(createdName);
        user.setUpdateDate(addUserRequest.getUpdateDate());
        user.setUpdateBy(addUserRequest.getUpdateBy());
        user.setUnit(unitService.findByUnitId(addUserRequest.getUnitId()).get());
        user.setStatus(addUserRequest.getUserStatus());
        Set<String> strRoles = addUserRequest.getListRoles();
        Set<RoleEntity> listRoles = new HashSet<>();
        if (strRoles == null) {
            // Set quyen mac dinh la user
            RoleEntity userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    // Xet quyen admin
                    case "admin":
                        RoleEntity adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                        break;
                    // Xet quyen user
                    case "user":
                        RoleEntity userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                        break;
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.badRequest().body(new MessageResponse("User registed successfully"));

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPass())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();

            String jwt = tokenProvider.generateToken(customUserDetail);
            List<String> listRoles = customUserDetail.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
//            return ResponseEntity.ok(
//                    new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getAddress(), listRoles));
            JwtResponse jwtResponse=new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getAddress(), listRoles);
            System.out.println(jwtResponse);
            return ResponseEntity.ok(new MessageResponse("Login Success"));
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse("Sai Username hoac password"));
        }
    }


    @GetMapping("/all-user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> showListUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/delete-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody UserEntity user) {
        try {
            userService.delete(user);
            return ResponseEntity.ok("Da xoa thanh cong");
        } catch (Exception e) {
            return ResponseEntity.ok("Khong ton tai");
        }
    }

    @PutMapping("/update-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUnit(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/filter/name={name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsersByName(@PathVariable String name){
        return ResponseEntity.ok(userService.findByUserName(name));
    }

    @GetMapping("/filter/address={address}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsersByAddress(@PathVariable String address){
        return ResponseEntity.ok(userService.findByUserName(address));
    }
}