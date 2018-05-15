package pl.ust.school.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.entity.Student;

@RunWith(SpringRunner.class) 
@DataJpaTest
public class StudentJPATest {
	 
	 @Autowired 
	 private TestEntityManager tem;
	 
	 private Student student;
	 
	@Before
	public void setUp() {

		student = new Student();
		student.setFirstName("John");
		student.setLastName("BrownXYZ");
		student.setEmail("johnXYZ@gamil.com");
		student.setPassword("123");
		student.setTelephone("1234567");
		student.setBirthDate(LocalDate.of(2000, 1, 1));
		student.setAddress("London, England");
	}
     // the test doesnt use  studentRepository - will fail if brak @Entity, or @Id @GeneratedValue
	@Test
	public void mapping() { 
		
		Student persisted = this.tem.persistAndFlush(this.student);
		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getId()).isGreaterThan(0);
		assertThat(persisted.getLastName()).isEqualTo(this.student.getLastName());
		
		/* Other useful methods
		 * Long id = (Long) this.tem.getId(persisted);
		Student found = this.tem.find(Student.class, 1L);
		Long studentId = this.tem.persistAndGetId(this.student, Long.class);
		Student found2 = this.tem.persistFlushFind(this.student);*/
	}

	   
}
