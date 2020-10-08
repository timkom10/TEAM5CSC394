package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class ManagerServiceImp implements ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerServiceImp(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public void addManager(Users user, Manager manager) {
        if((user != null) && (manager != null)) {
            manager.setManagerRole(user.getRoles());
            manager.setUserName(user.getUsername());
            this.managerRepository.save(manager);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.managerRepository.existsByUserName(username);
    }

    @Override
    public Manager getByUsername(String username) {
        Manager manager = this.managerRepository.findByUserName(username);
        if(manager != null) {
            return  manager;
        }
        return new Manager();
    }

    @Override
    public Set<Manager> findManagersByUsernameLike(String username) {
        return this.managerRepository.findAllByUserNameLike("%" + username + "%");
    }
}
