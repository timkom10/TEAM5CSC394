package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.feedback.AllFeedback;
import com.driver.workFlowManager.model.feedback.Feedback;

import java.util.List;

public interface FeedbackService {
    void addFeedback(Feedback feedback, String to, String from);
    List<AllFeedback> getAllFeedbackSortedByDate();
}
