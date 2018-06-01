package pl.ust.school.subject;

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

import pl.ust.school.subject.Subject;
import pl.ust.school.subject.SubjectRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubjectRepositoryTest {

	@Autowired
	SubjectRepository subjectRepo;

	private Subject subject;

	@Before
	public void setUp() {

		subject = new Subject();
		subject.setName("Quantum Physics");
	}

	@Test
	public void shouldFindSubjectById() {

		// given
		subjectRepo.save(this.subject);

		// when
		Optional<Subject> opt = subjectRepo.findById(this.subject.getId());
		Subject found = opt.get();

		// then
		assertThat(this.subject.getId()).isEqualTo(found.getId());
	}

	@Test
	public void shouldFindSubjectByName() {

		// given
		subjectRepo.save(this.subject);

		// when
		Collection<Subject> retrieved = subjectRepo.findByName((this.subject.getName()));

		// then
		assertThat(retrieved).hasSize(1);
		assertThat(retrieved).first().isEqualTo(this.subject);
	}
	
	@Test
	public void shouldThrowExceptionWhenSavingSubjectWithNonUniqueName() {
		
			assertThatCode(() -> {
				//when
				subjectRepo.save(this.subject);
	    		//then
	    	}).doesNotThrowAnyException();
			
			
			//given
			Subject differentSubject = new Subject();
			differentSubject.setName("Unique name");
			
			assertThatCode(() -> {
				//when
				subjectRepo.save(differentSubject);
	    		//then
	    	}).doesNotThrowAnyException();
			
			
			//given
			Subject sameName = new Subject();
			sameName.setName(this.subject.getName());
    		    	
			//when
    		Throwable thrown = catchThrowable(() -> { 
    			subjectRepo.save(sameName); 
    		});
    		
    		// then
    		assertThat(thrown).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
    }
	
	
	
	@Test
	public void shouldNotReturnSubject_WhenIsDeletedIsTrue() {
		
		// given
		subjectRepo.save(this.subject);
		
		Subject deleted = new Subject();
		deleted.setName("Maths");
		deleted.setDeleted(true);
		subjectRepo.save(deleted);
		
		//then 
		Iterable<Subject> list = subjectRepo.findAll();
		assertThat(list).size().isEqualTo(1);
		
		
	}

}
