package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.workers.Users;

import java.util.Set;

public interface UserService {
    Set<Users> findByUsername(String username);
    Users getByUsername(String username);
    void removeUser(String username);
    void simpleSaveUserInRoleRepo(String username);
    boolean addUser(Users user);
}
