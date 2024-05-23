package com.net.usermanager.service;

import com.net.usermanager.entity.UserEntity;
import com.net.usermanager.payload.response.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity findByUserName(String username);
    boolean existsByUserName(String username);
    UserEntity saveOrUpdate(UserEntity user);
    List<UserEntity> findAll();

    void delete(UserEntity user);

    Optional<UserEntity> findById(Long id);

    List<UserEntity> findAllUserByFullName(String userName);

    List<UserEntity> findAllUserByDateOfBirth(Date start,Date end);

    List<UserEntity> findAllUserByAddress(String address);

    List<UserDTO> getAllUsers();
}
