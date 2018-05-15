package pl.ust.school.app;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.entity.Subject;
import pl.ust.school.entity.TestEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT) // loads the whole context
@RunWith(SpringRunner.class)
public class IntegrationTest {
	
	 @Autowired
	    private TestRestTemplate restTemplate;

	@Test
	 public void shouldReturnStudentDetails() throws Exception {
		TestEntity entity = new TestEntity();
		
		ResponseEntity<TestEntity> responseEntity = restTemplate.postForEntity("/test/save",entity, TestEntity.class);
		responseEntity = restTemplate.getForEntity("/test/view/1", TestEntity.class);
		 
		assertThat(responseEntity.getBody().getId()).isGreaterThan(0);
		
	}

}
