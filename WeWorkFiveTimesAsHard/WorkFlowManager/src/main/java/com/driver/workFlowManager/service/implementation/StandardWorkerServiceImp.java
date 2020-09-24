package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.*;
import com.driver.workFlowManager.service.StandardWorkerService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
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
            standardWorkerSet.add(this.standardWorkerRepository.findByUserName(p.getUsername()));
        }
        return standardWorkerSet;
    }

    @Override
    public List<StandardWorker> getAllStandardWorkersSortedByPoints()
    {
        List<StandardWorker> standardWorkers = this.standardWorkerRepository.findAll();
        standardWorkers.sort(compareByTotalPoints);
        return standardWorkers;
    }

    @Override
    public List<StandardWorker> getAllStandardWorkersSortedByPointsByProject(Project project)
    {
        List<StandardWorker> standardWorkers = this.standardWorkerRepository.findAllByProject(project);
        standardWorkers.sort(compareByTotalPoints);
        return standardWorkers;
    }


    @Override
    public StandardWorker getByUsername(String username) {
        StandardWorker standardWorker = this.standardWorkerRepository.findByUserName(username);
        if(standardWorker != null) {
            return  standardWorker;
        }
        return new StandardWorker();
    }

    @Override
    public void insertAlteredStandardWorker(StandardWorker standardWorker, String username) {
        StandardWorker modifyThis = this.standardWorkerRepository.findByUserName(username);
        if((standardWorker != null) && (modifyThis != null))
        {
            modifyThis.setFirstName(standardWorker.getFirstName());
            modifyThis.setLastName(standardWorker.getLastName());
            modifyThis.setEmployeeRole(standardWorker.getEmployeeRole());
            modifyThis.setManagerUsername(standardWorker.getManagerUsername());
            this.standardWorkerRepository.save(modifyThis);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.standardWorkerRepository.existsByUserName(username);
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

    public static final Comparator<StandardWorker> compareByTotalPoints = (s1, s2) -> {
        //descending order
        return s2.getTotalPoints() - s1.getTotalPoints();
    };
}
