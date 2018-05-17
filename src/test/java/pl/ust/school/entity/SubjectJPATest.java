package pl.ust.school.entity;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
	public void shouldMapCorrectly_WhenSaving() { 
		
		Long subjectId = this.tem.persistAndGetId(this.subject, Long.class);
		assertThat(subjectId).isNotNull();
		assertThat(this.tem.find(Subject.class, subjectId).getName()).isEqualTo(this.subject.getName());
	}
	
	@Test
	public void shouldNotFindSubjects_WhenIsDeletedSetToTrue() {  
		
		//given
		Subject deleted = new Subject();
		deleted.setName("Subject3");
		deleted.setDeleted(true);
		
		int noOfNotDeletedSubjects = 2;
		
		Subject notDeleted1 = new Subject();
		notDeleted1.setName("Subject1");
		notDeleted1.setDeleted(false);
		
		Subject notDeleted2 = new Subject();
		notDeleted2.setName("Subject2");
		notDeleted2.setDeleted(false);
		
		this.tem.persistAndFlush(notDeleted1);
		this.tem.persistAndFlush(notDeleted2);
		this.tem.persistAndFlush(deleted);
		
		//then
	    List<Subject> subjects = this.tem.getEntityManager()
	    		.createQuery("select s from Subject s", Subject.class)
	    		.getResultList();
	    		
	    assertThat(subjects.size()).isEqualTo(noOfNotDeletedSubjects);
	}

}
