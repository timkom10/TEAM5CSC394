package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByUsername(String username);

}
