package pl.ust.school.subject;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.subject.Subject;
import pl.ust.school.teacher.Teacher;

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
	public void shouldNotLoadSubjects_WhenIsDeletedSetToTrue() {  
		
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
		
		this.tem.persistAndFlush(deleted);
		this.tem.persistAndFlush(notDeleted1);
		this.tem.persistAndFlush(notDeleted2);
		
		//then
	    List<Subject> subjects = this.tem.getEntityManager()
	    		.createQuery("select s from Subject s", Subject.class)
	    		.getResultList();
	    		
	    assertThat(subjects.size()).isEqualTo(noOfNotDeletedSubjects);
	}

	

	@SuppressWarnings("unused")
	private Teacher createTeacher(String name) {

		Teacher teacher = new Teacher();
		teacher = new Teacher();
		teacher.setFirstName("Jessica");
		teacher.setLastName("Motgmomery");
		teacher.setEmail(name + "@gamil.com");
		teacher.setPassword("567");
		teacher.setTelephone("1234567");
		teacher.setBirthDate(LocalDate.of(2000, 1, 1));
		teacher.setAddress("Manchester, England");

		return teacher;
	}

}
