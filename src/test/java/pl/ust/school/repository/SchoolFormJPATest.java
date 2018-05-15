package pl.ust.school.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
	public void mapping() { 
		
		Long schoolFormId = this.tem.persistAndGetId(this.schoolForm, Long.class);
		assertThat(schoolFormId).isNotNull();
		assertThat(this.tem.find(SchoolForm.class, schoolFormId).getName()).isEqualTo(this.schoolForm.getName());
		
	}


	
}
