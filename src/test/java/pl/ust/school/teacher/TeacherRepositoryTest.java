package pl.ust.school.teacher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.teacher.Teacher;
import pl.ust.school.teacher.TeacherRepository;

@RunWith(SpringRunner.class)
@DataJpaTest 
public class TeacherRepositoryTest {
	
	 @Autowired TeacherRepository teacherRepo;
	 
	 private Teacher teacher;
	 
	@Before
	public void setUp() {
		teacher = createTeacher("Alex");
	}
	
	private Teacher createTeacher(String name) {
		Teacher teacher = new Teacher();
		teacher = new Teacher();
		teacher.setFirstName(name);
		teacher.setLastName("Motgmomery");
		teacher.setEmail(name + "@gamil.com");
		teacher.setPassword("567");
		teacher.setTelephone("1234567");
		teacher.setBirthDate(LocalDate.of(2000, 1, 1));
		teacher.setAddress("Manchester, England");
		return teacher;
	}
	

	    @Test
	    public void shouldFindTeacherById()  {
	    	
	    	 //given
	    	teacherRepo.save(this.teacher);
	    	
	    	 //when
	    	Optional<Teacher> opt = teacherRepo.findById(this.teacher.getId());
	    	Teacher found = opt.get();
	    	
	    	//then
	    	assertThat(found.getLastName()).isEqualTo(this.teacher.getLastName());
	    }
	
	    @Test
		public void shouldFindTeacherByEmail() {
	    	
	    	System.err.println("----------------TEST EMAIL------------------");
	    	
	    	 //given
	    	teacherRepo.save(this.teacher);
	    	
	    	//when
	    	Optional<Teacher>  opt = teacherRepo.findByEmail(this.teacher.getEmail());
	    	Teacher found = opt.get();
	    	
	    	//then
			assertThat(found.getEmail()).isEqualTo(this.teacher.getEmail());
		}
	    
	  
	    @Test
		public void shouldFindTeacherByLastName() {
	    	
	    	 //given
	    	teacherRepo.save(this.teacher);
	    	
	    	//when
	    	Collection<Teacher> retrieved1 = teacherRepo.findByLastNameContains( (this.teacher.getLastName()));
	    	Collection<Teacher> retrieved2 = teacherRepo.findByLastNameContains((this.teacher.getLastName()).substring(2));
	    	
	    	//then
	    	assertThat(retrieved1).hasSize(1);
	    	assertThat(retrieved1).first().isEqualTo(this.teacher);
	    	
	    	assertThat(retrieved2).hasSize(1);
	    	assertThat(retrieved2).first().isEqualTo(this.teacher);
		}
	    
	    @Test
		public void shouldFindTeacherByFirstNameAndLastName() {
	    	
	    	 //given
	    	teacherRepo.save(this.teacher);
	    	
	    	//when
	    	Collection<Teacher> retrieved = teacherRepo.findByFirstNameAndLastName(this.teacher.getFirstName(), 
	    																				this.teacher.getLastName());
	    	
	    	//then
	    	assertThat(retrieved).hasSize(1);
	    	assertThat(retrieved).first().isEqualTo(this.teacher);
		}
	   
	    @Test
		public void shouldFindTop10SortedByLastNameAsc() {

	    	//given
	    	int noOfTeachers = 11;
	    	int noOfTeachersToRetrieve = 10;
	    	String leftOutTeacherName = "";
			List<Teacher> allTeachers = new ArrayList<Teacher>(noOfTeachers);

			for (int i = noOfTeachers; i >= 1; i--) { // added in desc order form 11Smith to 01Smith

				Teacher s = new Teacher();
		    	s.setFirstName(this.teacher.getFirstName());
		    	s.setEmail(this.teacher.getEmail() + i);
		    	s.setPassword(this.teacher.getPassword());
		    	s.setTelephone(this.teacher.getTelephone());
		    	s.setBirthDate(this.teacher.getBirthDate());
		    	s.setAddress(this.teacher.getAddress());
		    	
				s.setLastName(String.format("%02d", i) + "Smith");
				if(i == noOfTeachers) {
					leftOutTeacherName = String.format("%03d", i) + "Smith";
				}
				//System.err.println(s.getLastName()); 

				allTeachers.add(s); 
			}
						
			teacherRepo.saveAll(allTeachers);

			//when
			Collection<Teacher> foundAsc = teacherRepo.findTop10By((new Sort(ASC, "lastName")));
			
			//then
			assertThat(foundAsc).hasSize(noOfTeachersToRetrieve);
			assertThat(foundAsc).first().extracting("lastName").containsExactly("01" + "Smith");
			assertThat(foundAsc).last().extracting("lastName").containsExactly( noOfTeachersToRetrieve + "Smith");
			
			for (int i = 1; i <= noOfTeachers; i++) {
				assertThat( teacher.getLastName()).isNotEqualTo(leftOutTeacherName); 
			}
		}
	    
	    @Test
		public void shouldFindByLastNameOrderByEmailAsc() {

	    	//given
			int noOfTeachers = 11;
			int noOfTeachersExpected = 10;
			List<Teacher> allTeachers = new ArrayList<Teacher>(noOfTeachers);

			for (int i = noOfTeachers; i >= 1; i--) { // added in desc order from 11@gmail.com to 01@gmail.com

				Teacher s = new Teacher();
		    	s.setFirstName(this.teacher.getFirstName());
		    	s.setPassword(this.teacher.getPassword());
		    	s.setTelephone(this.teacher.getTelephone());
		    	s.setBirthDate(this.teacher.getBirthDate());
		    	s.setAddress(this.teacher.getAddress());
		    	
		    	s.setLastName(this.teacher.getLastName());
		    	if(i == 11) {
					s.setLastName("DifferentName");
				}
		    	
				s.setEmail(String.format("%02d", i) + "@gmail.com");
				
				allTeachers.add(s); 
			}
			
			teacherRepo.saveAll(allTeachers);
			
			//when
			
			// A slice of data that indicates whether there's a next or previous slice available. 
			//Allows to obtain a Pageable to request a previous or next Slice.
			Slice<Teacher> firstPage = teacherRepo.findByLastNameOrderByEmailAsc(this.teacher.getLastName(), 
																						PageRequest.of(0, 5)); 
																			// Basic Java Bean implementation of Pageable.int page, int size
			Slice<Teacher> secondPage = teacherRepo.findByLastNameOrderByEmailAsc(this.teacher.getLastName(), 
																						PageRequest.of(1, 6));
			
			//then
			assertThat(firstPage).first().extracting("email").containsExactly("01" + "@gmail.com");
			assertThat(secondPage).last().extracting("email").containsExactly(noOfTeachersExpected + "@gmail.com");
	    }
	    
	    
		@Test //TODO
		public void shouldThrowExceptionWhenSavingTeacherWithNonUniqueEmail() {
	    					
				assertThatCode(() -> {
					//when
		    		teacherRepo.save(this.teacher);
		    		//then
		    	}).doesNotThrowAnyException();
				
				//given
				Teacher differentEmail = new Teacher();
				differentEmail.setFirstName(this.teacher.getFirstName());
				differentEmail.setLastName(this.teacher.getLastName());
				differentEmail.setPassword(this.teacher.getPassword());
				differentEmail.setTelephone(this.teacher.getTelephone());
				differentEmail.setBirthDate(this.teacher.getBirthDate());
				differentEmail.setAddress(this.teacher.getAddress());
				differentEmail.setEmail(this.teacher.getEmail() + "foo");
				
				assertThatCode(() -> {
					//when
		    		teacherRepo.save(differentEmail);
		    		//then
		    	}).doesNotThrowAnyException();
				
				//given
				Teacher sameEmail = new Teacher();
				sameEmail.setFirstName(this.teacher.getFirstName());
				sameEmail.setLastName(this.teacher.getLastName());
				sameEmail.setPassword(this.teacher.getPassword());
				sameEmail.setTelephone(this.teacher.getTelephone());
				sameEmail.setBirthDate(this.teacher.getBirthDate());
				sameEmail.setAddress(this.teacher.getAddress());
				sameEmail.setEmail(this.teacher.getEmail());
	    		    	
				//when
	    		Throwable thrown = catchThrowable(() -> { 
	    			teacherRepo.save(sameEmail); 
	    		});
	    		
	    		// then
	    		assertThat(thrown).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
	    }
		
		@Test
		public void shouldNotReturnTeachers_WhenIsDeletedIsTrue() {
			
			// given
			teacherRepo.save(this.teacher);
			
			Teacher deleted = createTeacher("Dexter");
			deleted.setDeleted(true);
			teacherRepo.save(deleted);
			
			//then 
			Iterable<Teacher> list = teacherRepo.findAll();
			assertThat(list).size().isEqualTo(1);
		}
		
		
		
		
		
}
