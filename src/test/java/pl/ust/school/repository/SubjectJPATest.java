package pl.ust.school.repository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.entity.Subject;

@RunWith(SpringRunner.class) 
@DataJpaTest
public class SubjectJPATest {
	
	@Autowired 
	private TestEntityManager tem;
	
	private Subject subject;

	@Before
	public void setUp() {

		subject = new Subject();
		subject.setName("Quantum Physics");
	}
	
	@Test
	public void mapping() { 
		
		Long subjectFormId = this.tem.persistAndGetId(this.subject, Long.class);
		assertThat(subjectFormId).isNotNull();
		assertThat(this.tem.find(Subject.class, subjectFormId).getName()).isEqualTo(this.subject.getName());
		
	}


}
