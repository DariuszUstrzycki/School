package pl.ust.school.teacher;

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

import pl.ust.school.teacher.Teacher;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TeacherJPATest {

	@Autowired
	private TestEntityManager tem;

	private Teacher teacher;

	@Before
	public void setUp() {

		teacher = createTeacher("Jeff"); 
	}

	@Test
	public void shouldMapCorrectlyWhenSaving() {

		Teacher persisted = this.tem.persistAndFlush(this.teacher);
		assertThat(persisted.getLastName()).isEqualTo(this.teacher.getLastName());
		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getId()).isGreaterThan(0);

		/*
		 * Other useful methods Long id = (Long) this.tem.getId(persisted); Teacher
		 * found = this.tem.find(Teacher.class, 1L); Long teacherId =
		 * this.tem.persistAndGetId(this.teacher, Long.class); Teacher found2 =
		 * this.tem.persistFlushFind(this.teacher);
		 */
	}
	
	@Test
	public void shouldNotLoadTeachers_WhenIsDeletedSetToTrue() {  
		
		//given
		Teacher deleted = createTeacher("Andy");
		deleted.setDeleted(true);
		
		int noOfNotDeletedTeachers = 2;
		
		Teacher notDeleted1 = createTeacher("Mike");
		notDeleted1.setDeleted(false);
		
		Teacher notDeleted2 = createTeacher("Larry");
		notDeleted2.setDeleted(false);
		
		this.tem.persistAndFlush(deleted);
		this.tem.persistAndFlush(notDeleted1);
		this.tem.persistAndFlush(notDeleted2);
		
		
		//then
	    List<Teacher> teachers = this.tem.getEntityManager()
	    		.createQuery("select s from Teacher s", Teacher.class)
	    		.getResultList();
	    		
	    assertThat(teachers.size()).isEqualTo(noOfNotDeletedTeachers);
	}
	
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
