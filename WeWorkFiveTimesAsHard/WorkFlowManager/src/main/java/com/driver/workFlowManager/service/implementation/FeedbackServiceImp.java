package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.feedback.Feedback;
import com.driver.workFlowManager.model.feedback.AllFeedback;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.AllFeedbackRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeedbackServiceImp implements FeedbackService {
    private final StandardWorkerRepository standardWorkerRepository;
    private final AllFeedbackRepository allFeedbackRepository;

    public FeedbackServiceImp(StandardWorkerRepository standardWorkerRepository, AllFeedbackRepository allFeedbackRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
        this.allFeedbackRepository = allFeedbackRepository;
    }

    @Override
    public void addFeedback(Feedback feedback, String to, String from)
    {
        if(this.standardWorkerRepository.existsByUserName(to) && feedback != null) {
            StandardWorker standardWorker = this.standardWorkerRepository.findByUserName(to);
            feedback.setTo(to);
            feedback.setFrom(from);
            standardWorker.addFeedback(feedback);
            standardWorkerRepository.save(standardWorker);

            AllFeedback allFeedback = new AllFeedback();
            allFeedback.setFrom(from);
            allFeedback.setTo(to);
            allFeedback.setContent(feedback.getContent());
            allFeedback.setSubject(feedback.getSubject());
            allFeedback.setDate(new Date());
            allFeedbackRepository.save(allFeedback);
        }
    }

    @Override
    public List<AllFeedback> getAllFeedbackSortedByDate()
    {
        List<AllFeedback> sortedFeedback = (List<AllFeedback>) this.allFeedbackRepository.findAll();
        sortedFeedback.sort(compareByDate);
        return sortedFeedback;
    }


    public static final Comparator<AllFeedback> compareByDate= (f1, f2) -> {

        //Newest at the top, older feedback goes to the bottom of the screen
        return f2.getDate().compareTo(f1.getDate());
    };

}
