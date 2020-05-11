package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Model.User.Users;
import org.springframework.data.repository.CrudRepository;

                                //extends <ConcreteClass, type of the primary key>
public interface UsersRepository extends CrudRepository<Users, Long> {
}
