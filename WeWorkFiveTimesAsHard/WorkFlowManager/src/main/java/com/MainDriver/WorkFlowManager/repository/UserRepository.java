package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByUsername(String username);
    Set<Users> findByUsernameLike(String username);

}
