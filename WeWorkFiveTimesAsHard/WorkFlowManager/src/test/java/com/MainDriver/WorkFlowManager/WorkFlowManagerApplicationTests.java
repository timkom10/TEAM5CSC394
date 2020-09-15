package com.MainDriver.WorkFlowManager;

import io.hypersistence.optimizer.HypersistenceOptimizer;
import io.hypersistence.optimizer.core.config.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@SpringBootTest
class WorkFlowManagerApplicationTests {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void init(){
		new HypersistenceOptimizer(
				new JpaConfig(entityManagerFactory)
		);
	}

	@Test
	void contextLoads() {
	}

}
