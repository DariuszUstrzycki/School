package pl.ust.school.schoolform;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.schoolform.Schoolform;

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
	public void shouldNotLoadSchoolformsWhenIsDeletedSetToTrue() {  
		
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
	
	

	
}
