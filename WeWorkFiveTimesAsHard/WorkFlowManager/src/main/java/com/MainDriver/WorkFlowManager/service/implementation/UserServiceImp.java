package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.workers.Admin;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.repository.AdminRepository;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.repository.UserRepository;
import com.MainDriver.WorkFlowManager.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StandardWorkerRepository standardWorkerRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, StandardWorkerRepository standardWorkerRepository, ManagerRepository managerRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.standardWorkerRepository = standardWorkerRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public Set<Users> findByUsernameExcludeSelf(String username, String self) {
        Set<Users> users = this.userRepository.findByUsernameLike( "%" + username + "%");
        if(users != null) {
            for(Users user : users) {
                if(user.getUsername().equals(self)) {
                    users.remove(user);
                    if( users != null) { return users; }
                    break;
                }
            }
        }
        return new HashSet<Users>();
    }

    @Override
    public Set<Users> findByUsername(String username) {
        return  userRepository.findByUsernameLike("%" + username + "%");
    }

    @Override
    public Set<Users> findManagersByUsernameLike(String username) {
        return userRepository.findAllByRolesAndUsernameLike("MANAGER", "%" + username + "%");
    }

    @Override
    public void removeUser(String username) {
        Users user = userRepository.findByUsername(username);
        if(user != null){
            userRepository.delete(user);
        }
    }

    @Override
    public void simpleSaveUserInRoleRepo(String username) {
        if(this.standardWorkerRepository.existsByUserName(username))
        {
            StandardWorker standardWorker = standardWorkerRepository.findByuserName(username);
            this.standardWorkerRepository.save(standardWorker);
        }
        else if(this.managerRepository.existsByUserName(username))
        {
            Manager manager = this.managerRepository.findByUserName(username);
            this.managerRepository.save(manager);
        }
        else if(this.adminRepository.existsByUserName(username))
        {
            Admin admin = this.adminRepository.findByUserName(username);
            this.adminRepository.save(admin);
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
