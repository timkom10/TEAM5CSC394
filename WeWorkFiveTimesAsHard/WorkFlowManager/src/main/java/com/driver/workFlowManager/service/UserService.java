package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.workers.Users;

import java.util.Set;

public interface UserService {
    Set<Users> findByUsername(String username);
    Set<Users> findManagersByUsernameLike(String username);
    Users getByUsername(String username);
    void removeUser(String username);
    void simpleSaveUserInRoleRepo(String username);
    boolean addUser(Users user);
}
