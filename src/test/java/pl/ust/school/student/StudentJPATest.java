package pl.ust.school.student;

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

import pl.ust.school.student.Student;

@RunWith(SpringRunner.class) 
@DataJpaTest
public class StudentJPATest {
	 
	 @Autowired 
	 private TestEntityManager tem;
	 
	 private Student student;
	 
	@Before
	public void setUp() {

		student = createStudent("Lucy");
	}

	@Test
	public void shouldMapCorrectly_WhenSaving() { 
		
		Student persisted = this.tem.persistAndFlush(this.student);
		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getId()).isGreaterThan(0);
		assertThat(persisted.getLastName()).isEqualTo(this.student.getLastName());
	}
	
	@Test
	public void shouldNotLoadStudents_WhenIsDeletedSetToTrue() {  
		
		//given
		Student deleted = createStudent("Andy");
		deleted.setDeleted(true);
		
		int noOfNotDeletedStudents = 2;
		
		Student notDeleted1 = createStudent("Mike");
		notDeleted1.setDeleted(false);
		
		Student notDeleted2 = createStudent("Larry");
		notDeleted2.setDeleted(false);
		
		this.tem.persistAndFlush(notDeleted1);
		this.tem.persistAndFlush(notDeleted2);
		this.tem.persistAndFlush(deleted);
		
		//then
	    List<Student> students = this.tem.getEntityManager()
	    		.createQuery("select s from Student s", Student.class)
	    		.getResultList();
	    		
	    assertThat(students.size()).isEqualTo(noOfNotDeletedStudents);
	}
	
	
	private Student createStudent(String name) {
		
		Student student = new Student();
		student = new Student();
		student.setFirstName("Jessica");
		student.setLastName("Motgmomery");
		student.setEmail(name + "@gamil.com");
		student.setPassword("567");
		student.setTelephone("1234567");
		student.setBirthDate(LocalDate.of(2000, 1, 1));
		student.setAddress("Manchester, England");
		
		return student;
	}

	
	

	   
}
