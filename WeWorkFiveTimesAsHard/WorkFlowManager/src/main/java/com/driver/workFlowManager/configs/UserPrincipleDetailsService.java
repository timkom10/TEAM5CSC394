package com.driver.workFlowManager.configs;

import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipleDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipleDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users users = this.userRepository.findByUsername(s);
        if (users == null) {
            throw new UsernameNotFoundException("No user found for "+ s);
        }
        UserPrinciple userPrinciple = new UserPrinciple(users);
        return userPrinciple;
    }
}
