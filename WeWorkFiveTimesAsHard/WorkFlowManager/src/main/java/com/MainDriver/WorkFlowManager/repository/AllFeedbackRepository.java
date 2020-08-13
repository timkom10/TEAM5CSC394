package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.feedback.AllFeedback;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AllFeedbackRepository extends CrudRepository<AllFeedback, Long> {
}
