package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
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


    private final StandardWorkerRepository standardWorkerRepository;

    public StandardWorkerServiceImp(StandardWorkerRepository standardWorkerRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
    }

    @Override
    public List<StandardWorker> getAllFreeWorkersByUsername(String username)
    {
        return this.standardWorkerRepository.findAllByManagerAndUserNameLike(null, "%" + username + "%");
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
    public List<StandardWorker> getAllStandardWorkerByManager(Manager manager) {
        return this.standardWorkerRepository.findAllByManager(manager);
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
    public boolean existsByUsername(String username) {
        return this.standardWorkerRepository.existsByUserName(username);
    }

    @Override
    public void markTaskComplete(String username, Task task) {
        StandardWorker standardWorker = getByUsername(username);
        if(standardWorker != null) {
            standardWorker.didTask(task);
            this.standardWorkerRepository.save(standardWorker);
        }
    }

    public static final Comparator<StandardWorker> compareByTotalPoints = (s1, s2) -> {
        //descending order
        return s2.getTotalPoints() - s1.getTotalPoints();
    };
}
