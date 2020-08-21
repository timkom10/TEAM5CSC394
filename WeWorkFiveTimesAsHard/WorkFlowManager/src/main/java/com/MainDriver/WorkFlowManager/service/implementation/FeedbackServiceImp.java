package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.feedback.AllFeedback;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AllFeedbackRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.FeedbackService;
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
            StandardWorker standardWorker = this.standardWorkerRepository.findByuserName(to);
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
    /* Public feedback needs to be displayed by date newest first, the all feedback needs a date object */
    @Override
    public List<AllFeedback> getAllFeedbackSortedByDate()
    {
        List<AllFeedback> sortedFeedback = (List<AllFeedback>) this.allFeedbackRepository.findAll();
        Collections.sort(sortedFeedback,compareByDate);
        return sortedFeedback;
    }


    public static Comparator<AllFeedback> compareByDate= (f1, f2) -> {

        //Newest at the top, older feedback goes to the bottom of the screen
        return f2.getDate().compareTo(f1.getDate());
    };

}
