package pl.ust.school.schoolform;

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

import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.schoolform.SchoolformRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SchoolformRepositoryTest {

	@Autowired
	SchoolformRepository schoolformRepo;

	private Schoolform schoolform;

	@Before
	public void setUp() {

		schoolform = new Schoolform();
		schoolform.setName("First Year");
	}

	@Test
	public void shouldFindSchoolformById() {

		// given
		schoolformRepo.save(this.schoolform);

		// when
		Optional<Schoolform> opt = schoolformRepo.findById(this.schoolform.getId());
		Schoolform found = opt.get();

		// then
		assertThat(this.schoolform.getId()).isEqualTo(found.getId());
	}

	@Test
	public void shouldFindSchoolformByName() {

		// given
		schoolformRepo.save(this.schoolform);

		// when
		Collection<Schoolform> retrieved = schoolformRepo.findByName((this.schoolform.getName()));

		// then
		assertThat(retrieved).hasSize(1);
		assertThat(retrieved).first().isEqualTo(this.schoolform);
	}
	
	@Test
	public void shouldThrowExceptionWhenSavingSchoolformWithNonUniqueEmail() {
		
			assertThatCode(() -> {
				//when
				schoolformRepo.save(this.schoolform);
	    		//then
	    	}).doesNotThrowAnyException();
			
			
			//given
			Schoolform differentSchoolform = new Schoolform();
			differentSchoolform.setName("Unique name");
			
			assertThatCode(() -> {
				//when
				schoolformRepo.save(differentSchoolform);
	    		//then
	    	}).doesNotThrowAnyException();
			
			
			//given
			Schoolform sameName = new Schoolform();
			sameName.setName(this.schoolform.getName());
    		    	
			//when
    		Throwable thrown = catchThrowable(() -> { 
    			schoolformRepo.save(sameName); 
    		});
    		
    		// then
    		assertThat(thrown).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
    }
	
	@Test
	public void shouldNotReturnSchoolform_WhenIsDeletedIsTrue() {
		
		// given
		schoolformRepo.save(this.schoolform);
		
		Schoolform deleted = new Schoolform();
		deleted.setName("Third year");
		deleted.setDeleted(true);
		schoolformRepo.save(deleted);
		
		//then 
		Iterable<Schoolform> list = schoolformRepo.findAll();
		assertThat(list).size().isEqualTo(1);
		
		
	}
    	    
	

}
