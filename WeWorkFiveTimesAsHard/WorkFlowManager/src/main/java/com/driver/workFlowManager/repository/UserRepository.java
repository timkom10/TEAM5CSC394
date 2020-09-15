package com.driver.workFlowManager.repository;

import com.driver.workFlowManager.model.workers.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByUsername(String username);
    Set<Users> findByUsernameLike(String username);
    Set<Users> findAllByUsernameLike(String username);
    Set<Users> findAllByRolesAndUsernameLike(String role, String username);
}
