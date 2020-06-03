package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.Users;
import com.MainDriver.WorkFlowManager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
