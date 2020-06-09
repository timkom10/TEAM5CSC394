package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.feedback.Feedback;

public interface FeedbackService {
    void addFeedback(Feedback feedback, String to, String from);
}
