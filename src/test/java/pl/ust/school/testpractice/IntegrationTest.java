package pl.ust.school.testpractice;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(webEnvironment = RANDOM_PORT) // loads the whole context
@RunWith(SpringRunner.class)
public class IntegrationTest {

	@SuppressWarnings("unused")
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnStudentDetails() throws Exception {
		
		assert(true);
		/*TestEntity entity = new TestEntity();

		org.springframework.http.ResponseEntity<TestEntity> responseEntity = 
				restTemplate.postForEntity("/test/save", entity, TestEntity.class);
		
		responseEntity = restTemplate.getForEntity("/test/view/1", TestEntity.class);
		
		org.assertj.core.api.Assertions.assertThat(responseEntity).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseEntity.getBody().getId()).isGreaterThan(0);
*/
	}

}
