package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class ManagerServiceImp implements ManagerService {
    private final ManagerRepository managerRepository;
    private final StandardWorkerRepository standardWorkerRepository;

    public ManagerServiceImp(ManagerRepository managerRepository, StandardWorkerRepository standardWorkerRepository) {
        this.managerRepository = managerRepository;
        this.standardWorkerRepository = standardWorkerRepository;
    }

    @Override
    public void removeWorkerFromManager(String workerUsername, String managerUsername) {

        StandardWorker standardWorker = this.standardWorkerRepository.findByUserName(workerUsername);
        Manager manager = this.managerRepository.findByUserName(managerUsername);

        if(standardWorker != null && manager != null ) {
            manager.getDominion().remove(standardWorker);
            this.managerRepository.save(manager);

            standardWorker.setManager(null);
            standardWorker.setProject(null);
            this.standardWorkerRepository.save(standardWorker);
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
