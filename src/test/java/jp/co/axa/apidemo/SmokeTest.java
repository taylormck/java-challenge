package jp.co.axa.apidemo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.axa.apidemo.controllers.EmployeeController;

@SpringBootTest
public class SmokeTest {
  
	@Autowired
	private EmployeeController employeeController;

	@Test
	public void employeeControllerLoads() throws Exception {
		assertThat(employeeController).isNotNull();
	}
}