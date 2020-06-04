package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.Users;
import com.MainDriver.WorkFlowManager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Set<Users> findByUsername(String username) {
        return  userRepository.findByUsernameLike("%" + username + "%");
    }

    public void removeUser(String username) {
        Users user = userRepository.findByUsername(username);
        if(user != null){
            userRepository.delete(user);
        }
    }

    public Users getByUsername(String username)
    {
        return this.userRepository.findByUsername(username);
    }

    public boolean addUser(Users user)
    {
        if(userRepository.findByUsername(user.getUsername()) == null)
        {
            //can insert
            String plainTextPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(plainTextPassword));
            user.setPermissions("");
            user.setActive(1);
            userRepository.save(user);
            return true;
        }
        //no insert
        return false;
    }
}
