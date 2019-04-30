package org.springframework.fridgetracker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.fridgetracker.SpringFridgetrackerApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringFridgetrackerApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringFridgetrackerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
