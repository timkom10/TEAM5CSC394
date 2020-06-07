package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.repository.UserRepository;
import com.MainDriver.WorkFlowManager.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Set<Users> findByUsername(String username) {
        return  userRepository.findByUsernameLike("%" + username + "%");
    }

    @Override
    public void removeUser(String username) {
        Users user = userRepository.findByUsername(username);
        if(user != null){
            userRepository.delete(user);
        }
    }

    @Override
    public Users getByUsername(String username)
    {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public boolean addUser(Users user) {
        if(userRepository.findByUsername(user.getUsername()) == null) {
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
