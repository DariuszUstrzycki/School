package pl.ust.school.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
/*
@RunWith(SpringRunner.class) 
@DataJpaTest*/
public class JPA_TSSTest {
	/*
	@Autowired 
	 private TestEntityManager tem;*/
	 
	 //private TSS ts;
	 
	/*@Before
	public void setUp() {

		ts = createStudent("Lucy");
	}*/
    // the test doesnt use  studentRepository - will fail if brak @Entity, or @Id @GeneratedValue
	
	
	/*
	 @Ignore @Test //TODO
	public void shouldRemoveTeachersAndSubjects_When_TSS_IsDeleted() {
		//arrange
				Subject subject1 = new Subject();
				subject1.setName("Maths");
				Teacher teacher1 = createTeacher("John");
				TSS ts1 = new TSS();
				ts1.setTeacher(teacher1);
				ts1.setSubject(subject1);
				
				Subject subject2 = new Subject();
				subject2.setName("Biology");
				TSS ts2 = new TSS();
				ts2.setTeacher(teacher1);
				ts2.setSubject(subject2);
				
				assertThat(teacher1.getTeacherSubjects().size()).isEqualTo(2);
				
				//act
				ts1.remove();
				
				//assert
				assertThat(teacher1.getTeacherSubjects().size()).isEqualTo(1);
		
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
*/
}
