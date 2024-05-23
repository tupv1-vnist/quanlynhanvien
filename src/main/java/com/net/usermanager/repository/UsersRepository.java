package com.net.usermanager.repository;

import com.net.usermanager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUserName(String userName);
    boolean existsByUserName(String userName);

    void delete(UserEntity user);


}
