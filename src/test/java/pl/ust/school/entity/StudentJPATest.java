package pl.ust.school.entity;

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

import pl.ust.school.entity.Student;

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
     // the test doesnt use  studentRepository - will fail if brak @Entity, or @Id @GeneratedValue
	@Test
	public void shouldMapCorrectly_WhenSaving() { 
		
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
	
	@Test
	public void shouldRemoveStudentFromSchoolForm_WhenStudentIsDeleted() {
		
		//assert
		Student notDeleted = createStudent("NotDeleted");
		SchoolForm form = new SchoolForm();
		form.setName("First Year");
		notDeleted.setSchoolForm(form);
		
		Student deleted = createStudent("deleted");
		deleted.setSchoolForm(form);
		
		assertThat(form.getStudents().size()).isEqualTo(2);
		
		//act
		deleted.remove();
		
		//assert
		assertThat(form.getStudents().size()).isEqualTo(1);
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
