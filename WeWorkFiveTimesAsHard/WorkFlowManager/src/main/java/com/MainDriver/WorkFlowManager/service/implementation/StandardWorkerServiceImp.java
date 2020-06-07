package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.*;
import com.MainDriver.WorkFlowManager.service.StandardWorkerService;
import org.springframework.stereotype.Service;
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
    public StandardWorker getByUsername(String username)
    {
        StandardWorker standardWorker = this.standardWorkerRepository.findByuserName(username);
        if(standardWorker != null) {
            return  standardWorker;
        }
        return new StandardWorker();
    }
    @Override
    public void insertAlteredStandardWorker(StandardWorker standardWorker, String username)
    {
        StandardWorker modifyThis = this.standardWorkerRepository.findByuserName(username);
        if((standardWorker != null) && (modifyThis != null))
        {
            System.out.println("Over writing" +modifyThis.getFirstName() + " with: " +standardWorker.getFirstName() );
            modifyThis.setFirstName(standardWorker.getFirstName());

            System.out.println("Over writing" +modifyThis.getLastName() + " with: " + standardWorker.getLastName() );
            modifyThis.setLastName(standardWorker.getLastName());

            System.out.println("Over writing" +modifyThis.getRole() + " with: " + standardWorker.getRole() );
            modifyThis.setEmployeeRole(standardWorker.getRole());

            System.out.println("Over writing" +modifyThis.getManagerUsername() + " with: " + standardWorker.getManagerUsername() );
            modifyThis.setManagerUsername(standardWorker.getManagerUsername());

            this.standardWorkerRepository.save(modifyThis);
        }
    }

    @Override
    public void addStandardWorker(Users user, StandardWorker standardWorker)
    {
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