package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.repository.AdminRepository;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.repository.UserRepository;
import com.driver.workFlowManager.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
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
    public Set<Users> findByUsername(String username) {
        return  userRepository.findAllByUsernameLike("%" + username + "%");
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
            StandardWorker standardWorker = standardWorkerRepository.findByUserName(username);
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
    public String getUserType(String username)
    {
        if(this.standardWorkerRepository.existsByUserName(username)) return "S";
        else if(this.managerRepository.existsByUserName(username)) return "M";
        else if(this.adminRepository.existsByUserName(username)) return "A";
        return null;
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

    @Override
    public boolean existsByUsername(String username)
    {
        return this.userRepository.existsByUsername(username);
    }
}
