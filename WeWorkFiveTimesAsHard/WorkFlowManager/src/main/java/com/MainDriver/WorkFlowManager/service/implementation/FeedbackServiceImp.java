package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.feedback.AllFeedback;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AllFeedbackRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImp implements FeedbackService {
    @Autowired
    StandardWorkerRepository standardWorkerRepository;

    @Autowired
    AllFeedbackRepository allFeedbackRepository;

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
            allFeedbackRepository.save(allFeedback);
        }
    }
}
