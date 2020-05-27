package com.MainDriver.WorkFlowManager.BootStrapData;

import com.MainDriver.WorkFlowManager.Model.Users;
import com.MainDriver.WorkFlowManager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
    PlaceHolder Data, forget about flyway....
 */
@Component
public class InitialData implements CommandLineRunner
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    )
    {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("Started in Bootstrap");
        this.userRepository.deleteAll();

        //Any new users need to have password encrypted before db insert
        Users peter = new Users("peter", passwordEncoder.encode("peter12"),"USER", "none");
        Users admin = new Users("admin", passwordEncoder.encode("admin12"),"ADMIN", "");
        Users manager = new Users("manager", passwordEncoder.encode("manager12"),"MANAGER", "");

       this.userRepository.save(peter);
       this.userRepository.save(admin);
       this.userRepository.save(manager);

        //check the Database
    }
}
