package com.MainDriver.WorkFlowManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorkFlowManagerApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(WorkFlowManagerApplication.class, args);
		System.out.println("No errors? - test");
	}
	@Bean
	/*Creates a table if it doesn't exist, classes and tables are interchangable*/
	CommandLineRunner runner(TestUserRepository TUR)
	{
		return args ->
		{
			TUR.save(new TestUser("Peter 2"));
		};
	}
}
