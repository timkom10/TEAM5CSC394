package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.*;
import com.MainDriver.WorkFlowManager.service.StandardWorkerService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class StandardWorkerServiceImp implements StandardWorkerService {

    private final ManagerRepository managerRepository;
    private final StandardWorkerRepository standardWorkerRepository;
    private final UserRepository userRepository;

    public StandardWorkerServiceImp(ManagerRepository managerRepository, StandardWorkerRepository standardWorkerRepository, UserRepository userRepository) {
        this.managerRepository = managerRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Set<StandardWorker> findAllByUsername(String username)
    {
        Set<StandardWorker> standardWorkerSet = new HashSet<>();
        for(Users p: this.userRepository.findAllByRolesAndUsernameLike("STANDARDWORKER", "%" + username + "%"))
        {
            standardWorkerSet.add(this.standardWorkerRepository.findByuserName(p.getUsername()));
        }
        return standardWorkerSet;
    }

    @Override
    @Transactional
    public Project getStandardWorkerProject(String username) {
        if(this.standardWorkerRepository.existsByUserName(username)) {
            StandardWorker standardWorker = this.standardWorkerRepository.findByuserName(username);
            return standardWorker.getProject();
        }
        return null;
    }

    @Override
    @Transactional
    public StandardWorker getByUsername(String username) {
        StandardWorker standardWorker = this.standardWorkerRepository.findByuserName(username);
        if(standardWorker != null) {
            return  standardWorker;
        }
        return new StandardWorker();
    }

    @Override
    public void insertAlteredStandardWorker(StandardWorker standardWorker, String username) {
        StandardWorker modifyThis = this.standardWorkerRepository.findByuserName(username);
        if((standardWorker != null) && (modifyThis != null))
        {
            modifyThis.setFirstName(standardWorker.getFirstName());
            modifyThis.setLastName(standardWorker.getLastName());
            modifyThis.setEmployeeRole(standardWorker.getRole());
            modifyThis.setManagerUsername(standardWorker.getManagerUsername());
            this.standardWorkerRepository.save(modifyThis);
        }
    }

    @Override
    public void addStandardWorker(Users user, StandardWorker standardWorker) {
        if((user !=null) &&(standardWorker != null)) {
            standardWorker.setEmployeeRole(user.getRoles());
            standardWorker.setUserName(user.getUsername());

            Manager manager = managerRepository.findByUserName(standardWorker.getManagerUsername());
            if(manager != null) {
                standardWorker.setManager(manager);
            }
            standardWorkerRepository.save(standardWorker);
        }
    }
}
