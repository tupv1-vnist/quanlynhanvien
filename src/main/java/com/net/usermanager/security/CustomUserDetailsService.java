package com.net.usermanager.security;


import com.net.usermanager.entity.UserEntity;
import com.net.usermanager.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không
        UserEntity user=usersRepository.findByUserName(username);
        if (user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return CustomUserDetails.mapUserToUserDetail(user);
    }
}
