package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.feedback.AllFeedback;
import com.MainDriver.WorkFlowManager.model.feedback.Feedback;

import java.util.List;

public interface FeedbackService {
    void addFeedback(Feedback feedback, String to, String from);
    List<AllFeedback> getAllFeedbackSortedByDate();
}
