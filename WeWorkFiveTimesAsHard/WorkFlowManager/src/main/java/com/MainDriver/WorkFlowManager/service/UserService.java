package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.workers.Users;

import java.util.Set;

public interface UserService {
    Set<Users> findByUsername(String username);
    void removeUser(String username);
    Users getByUsername(String username);
    boolean addUser(Users user);
}
