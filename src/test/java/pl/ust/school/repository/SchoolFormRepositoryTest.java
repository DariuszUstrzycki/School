package pl.ust.school.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.entity.SchoolForm;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SchoolFormRepositoryTest {

	@Autowired
	SchoolFormRepository schoolFormRepo;

	private SchoolForm schoolForm;

	@Before
	public void setUp() {

		schoolForm = new SchoolForm();
		schoolForm.setName("First Year");
	}

	@Test
	public void shouldFindSchoolFormById() {

		// given
		schoolFormRepo.save(this.schoolForm);

		// when
		Optional<SchoolForm> opt = schoolFormRepo.findById(this.schoolForm.getId());
		SchoolForm found = opt.get();

		// then
		assertThat(this.schoolForm.getId()).isEqualTo(found.getId());
	}

	@Test
	public void shouldFindSchoolFormByName() {

		// given
		schoolFormRepo.save(this.schoolForm);

		// when
		Collection<SchoolForm> retrieved = schoolFormRepo.findByName((this.schoolForm.getName()));

		// then
		assertThat(retrieved).hasSize(1);
		assertThat(retrieved).first().isEqualTo(this.schoolForm);
	}
	
	@Test
	public void shouldThrowExceptionWhenSavingSchoolFormWithNonUniqueEmail() {
		
			assertThatCode(() -> {
				//when
				schoolFormRepo.save(this.schoolForm);
	    		//then
	    	}).doesNotThrowAnyException();
			
			
			//given
			SchoolForm differentSchoolForm = new SchoolForm();
			differentSchoolForm.setName("Unique name");
			
			assertThatCode(() -> {
				//when
				schoolFormRepo.save(differentSchoolForm);
	    		//then
	    	}).doesNotThrowAnyException();
			
			
			//given
			SchoolForm sameName = new SchoolForm();
			sameName.setName(this.schoolForm.getName());
    		    	
			//when
    		Throwable thrown = catchThrowable(() -> { 
    			schoolFormRepo.save(sameName); 
    		});
    		
    		// then
    		assertThat(thrown).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
    }
	
	@Test
	public void shouldNotReturnSchoolForm_WhenIsDeletedIsTrue() {
		
		// given
		schoolFormRepo.save(this.schoolForm);
		
		SchoolForm deleted = new SchoolForm();
		deleted.setName("Third year");
		deleted.setDeleted(true);
		schoolFormRepo.save(deleted);
		
		//then 
		Iterable<SchoolForm> list = schoolFormRepo.findAll();
		assertThat(list).size().isEqualTo(1);
		
		
	}
    	    
	

}
