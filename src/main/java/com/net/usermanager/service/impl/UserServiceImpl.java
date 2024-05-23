package com.net.usermanager.service.impl;

import com.net.usermanager.entity.UserEntity;
import com.net.usermanager.payload.response.UserDTO;
import com.net.usermanager.repository.UsersRepository;
import com.net.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserEntity findByUserName(String username) {
        return usersRepository.findByUserName(username);
    }

    @Override
    public boolean existsByUserName(String username) {
        return usersRepository.existsByUserName(username);
    }

    @Override
    public UserEntity saveOrUpdate(UserEntity user) {
        return usersRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public void delete(UserEntity user) {
        usersRepository.delete(user);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public List<UserEntity> findAllUserByFullName(String userName) {
        List<UserEntity> userEntities=usersRepository.findAll();
        return userEntities.stream()
                .filter(userEntity -> userEntity.getUserName().toLowerCase().contains(userName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> findAllUserByDateOfBirth(Date start, Date end) {
        List<UserEntity> userEntities=usersRepository.findAll();
        return userEntities.stream()
                .filter(userEntity -> userEntity.getDateOfBirth().after(start) && userEntity.getDateOfBirth().before(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> findAllUserByAddress(String address) {
        List<UserEntity> userEntities=usersRepository.findAll();
        return userEntities.stream()
                .filter(userEntity -> userEntity.getAddress().toLowerCase().contains(address.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return null;
    }


}
