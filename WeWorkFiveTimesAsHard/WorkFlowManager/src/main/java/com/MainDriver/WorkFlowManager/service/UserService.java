package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.workers.Users;

import java.util.Set;

public interface UserService {
    Set<Users> findByUsernameExcludeSelf(String username, String self);
    Set<Users> findByUsername(String username);
    Set<Users> findManagersByUsernameLike(String username);
    Users getByUsername(String username);
    void removeUser(String username);
    void simpleSaveUserInRoleRepo(String username);
    boolean addUser(Users user);
}
