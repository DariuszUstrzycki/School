package pl.ust.school.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 
@DataJpaTest
public class SchoolformJPATest {
	
	@Autowired 
	private TestEntityManager tem;
	
	private Schoolform schoolform;

	@Before
	public void setUp() {

		schoolform = new Schoolform();
		schoolform.setName("First Year");
	}
	
	@Test
	public void shouldMapCorrectly_WhenSaving() { 
		
		Long schoolformId = this.tem.persistAndGetId(this.schoolform, Long.class);
		assertThat(schoolformId).isNotNull();
		assertThat(this.tem.find(Schoolform.class, schoolformId).getName()).isEqualTo(this.schoolform.getName());
		
	}
	
	@Test
	public void shouldNotLoadSchoolforms_WhenIsDeletedSetToTrue() {  
		
		//given
		Schoolform deleted = new Schoolform();
		deleted.setName("Schoolform3");
		deleted.setDeleted(true);
		
		int noOfNotDeletedSchoolforms = 2;
		
		Schoolform notDeleted1 = new Schoolform();
		notDeleted1.setName("Schoolform1");
		notDeleted1.setDeleted(false);
		
		Schoolform notDeleted2 = new Schoolform();
		notDeleted2.setName("Schoolform2");
		notDeleted2.setDeleted(false);
		
		this.tem.persistAndFlush(notDeleted1);
		this.tem.persistAndFlush(notDeleted2);
		this.tem.persistAndFlush(deleted);
		
		//then
	    List<Schoolform> schoolforms = this.tem.getEntityManager()
	    		.createQuery("select s from Schoolform s", Schoolform.class)
	    		.getResultList();
	    		
	    assertThat(schoolforms.size()).isEqualTo(noOfNotDeletedSchoolforms);
	}
	
	@Ignore @Test //TODO
	public void shouldSetStudentsSchoolformToNull_WhenSchoolformDeleted() {
		
		//arrange
		Student student1 = createStudent("John");
		Student student2 = createStudent("Mike");
		student1.setSchoolform(this.schoolform);
		student2.setSchoolform(this.schoolform);
		
		assertThat(this.schoolform.getStudents().size()).isEqualTo(2);
		assertThat(student1.getSchoolform()).isNotNull();
		assertThat(student2.getSchoolform()).isNotNull();
		/*
		//act
		this.schoolform.remove();
		*/
		//assert
		assertThat(this.schoolform.getStudents().size()).isEqualTo(0);
		assertThat(student1.getSchoolform()).isNull();
		assertThat(student2.getSchoolform()).isNull();
		
		
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
