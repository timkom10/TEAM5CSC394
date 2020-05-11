package com.MainDriver.WorkFlowManager.BootStrapData;

import com.MainDriver.WorkFlowManager.Model.User.Users;
import com.MainDriver.WorkFlowManager.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
    PlaceHolder before flyway is up and going
 */
@Component
public class InitialData implements CommandLineRunner
{

    private final UsersRepository usersRepository;

    public InitialData(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("Started in Bootstrap");
        Users users = new Users();
        users.setFirstName("Peter");
        users.setLastName("Gentile");
        users.setHireDate("02-20-2020");
        users.setRole("Writes stuff?");

        usersRepository.save(users);
        System.out.println("Amount of Users:" + usersRepository.count());
    }
}
