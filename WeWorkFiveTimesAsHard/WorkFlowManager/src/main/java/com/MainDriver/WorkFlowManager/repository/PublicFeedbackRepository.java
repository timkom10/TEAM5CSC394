package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.feedback.PublicFeedback;
import org.springframework.data.repository.CrudRepository;

public interface PublicFeedbackRepository extends CrudRepository<PublicFeedback, Long> {
}
