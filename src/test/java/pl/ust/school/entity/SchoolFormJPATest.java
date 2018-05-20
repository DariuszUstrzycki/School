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

import pl.ust.school.entity.SchoolForm;

@RunWith(SpringRunner.class) 
@DataJpaTest
public class SchoolFormJPATest {
	
	@Autowired 
	private TestEntityManager tem;
	
	private SchoolForm schoolForm;

	@Before
	public void setUp() {

		schoolForm = new SchoolForm();
		schoolForm.setName("First Year");
	}
	
	@Test
	public void shouldMapCorrectly_WhenSaving() { 
		
		Long schoolFormId = this.tem.persistAndGetId(this.schoolForm, Long.class);
		assertThat(schoolFormId).isNotNull();
		assertThat(this.tem.find(SchoolForm.class, schoolFormId).getName()).isEqualTo(this.schoolForm.getName());
		
	}
	
	@Test
	public void shouldNotLoadSchoolForms_WhenIsDeletedSetToTrue() {  
		
		//given
		SchoolForm deleted = new SchoolForm();
		deleted.setName("SchoolForm3");
		deleted.setDeleted(true);
		
		int noOfNotDeletedSchoolForms = 2;
		
		SchoolForm notDeleted1 = new SchoolForm();
		notDeleted1.setName("SchoolForm1");
		notDeleted1.setDeleted(false);
		
		SchoolForm notDeleted2 = new SchoolForm();
		notDeleted2.setName("SchoolForm2");
		notDeleted2.setDeleted(false);
		
		this.tem.persistAndFlush(notDeleted1);
		this.tem.persistAndFlush(notDeleted2);
		this.tem.persistAndFlush(deleted);
		
		//then
	    List<SchoolForm> schoolForms = this.tem.getEntityManager()
	    		.createQuery("select s from SchoolForm s", SchoolForm.class)
	    		.getResultList();
	    		
	    assertThat(schoolForms.size()).isEqualTo(noOfNotDeletedSchoolForms);
	}
	
	@Test
	public void shouldSetStudentsSchoolformToNull_WhenSchoolformDeleted() {
		
		//arrange
		Student student1 = createStudent("John");
		Student student2 = createStudent("Mike");
		student1.setSchoolForm(this.schoolForm);
		student2.setSchoolForm(this.schoolForm);
		
		assertThat(this.schoolForm.getStudents().size()).isEqualTo(2);
		assertThat(student1.getSchoolForm()).isNotNull();
		assertThat(student2.getSchoolForm()).isNotNull();
		
		//act
		this.schoolForm.remove();
		
		//assert
		assertThat(this.schoolForm.getStudents().size()).isEqualTo(0);
		assertThat(student1.getSchoolForm()).isNull();
		assertThat(student2.getSchoolForm()).isNull();
		
		
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
