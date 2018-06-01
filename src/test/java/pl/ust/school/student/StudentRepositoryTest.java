package pl.ust.school.student;

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

import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.schoolform.SchoolformRepository;
import pl.ust.school.student.Student;
import pl.ust.school.student.StudentRepository;
/*
In this test, we are using the H2 database for testing. This is common practice. Otherwise, 
you need to have the same type of database set up in all test/dev environments, maintain them 
and make sure you clean them up after test execution. This is not necessary when you use 
the H2 DB, because it is in the memory. After the test(s) is finished, the database will be fully dropped.
*/
// @ActiveProfiles("test") ?
@RunWith(SpringRunner.class) // blocks JUNIt test runner
@DataJpaTest  // removes default autoconfig, uses in-memo db, transactional, no cache, @PropertyMapping(value = "spring.jpa.show-sql")
// for a typical JPA test. Can be used when a test focuses only on JPA components. Using this annotation 
//will disable full auto-configuration and instead apply only configuration relevant to JPA tests.

//By default, tests annotated with @DataJpaTest will use an embedded in-memory database (replacing 
//any explicit or usually auto-configured DataSource). The @AutoConfigureTestDatabase annotation 
//can be used to override these settings. eg @AutoConfigureTestDatabase(replace = NONE)

//If you are looking to load your full application configuration, but use an embedded database, 
//you should consider @SpringBootTest combined with @AutoConfigureTestDatabase rather than this annotation.
public class StudentRepositoryTest {
	
	 @Autowired StudentRepository studentRepo;
	 @Autowired SchoolformRepository  schoolformRepo;
	 
	 private Student student;
	 
	@Before
	public void setUp() {

		student = createStudent("George");

	}
    
	    @Test  // will fail if repository doesnt exist  - Does it make sense?! Niby custom finder method...
	    public void shouldFindStudentById()  {
	    	
	    	 //given
	    	studentRepo.save(this.student);

	    	//when
	    	Optional<Student> opt = studentRepo.findById(this.student.getId());
	    	Student found = opt.get();
	    	
	    	//then
	    	assertThat(found.getLastName()).isEqualTo(this.student.getLastName());
	    	assertThat(this.student.getId()).isNotNull();
	    }
	    
	    @Test // will fail if repository doesnt have findByEmail(String email) method
		public void shouldFindStudentByEmail() {
	    	
	    	 //given
	    	studentRepo.save(this.student);
	    	
	    	//when
	    	Optional<Student>  opt = studentRepo.findByEmail(this.student.getEmail());
	    	Student found = opt.get();
	    	
	    	//then
			assertThat(found.getEmail()).isEqualTo(this.student.getEmail());
		}
	    
	    @Test
		public void shouldFindStudentByLastName() {
	    	
	    	 //given
	    	studentRepo.save(this.student);
	    	
	    	//when
	    	Collection<Student> retrieved1 = studentRepo.findByLastNameContains( (this.student.getLastName()));
	    	Collection<Student> retrieved2 = studentRepo.findByLastNameContains((this.student.getLastName()).substring(2));
	    	
	    	//then
	    	assertThat(retrieved1).hasSize(1);
	    	assertThat(retrieved1).first().isEqualTo(this.student);
	    	
	    	assertThat(retrieved2).hasSize(1);
	    	assertThat(retrieved2).first().isEqualTo(this.student);
		}
	    
	    @Test
		public void shouldFindStudentByFirstNameAndLastName() {
	    	
	    	 //given
	    	studentRepo.save(this.student);
	    	
	    	//when
	    	Collection<Student> retrieved = studentRepo.findByFirstNameAndLastName(this.student.getFirstName(), 
	    																				this.student.getLastName());
	    	
	    	//then
	    	assertThat(retrieved).hasSize(1);
	    	assertThat(retrieved).first().isEqualTo(this.student);
		}
	    
	    @Test
		public void shouldFindTop10SortedByLastNameAsc() {
	    	
	    	//given
	    	int noOfStudents = 11;
	    	int noOfStudentsToRetrieve = 10;
	    	String leftOutStudentName = "";
			List<Student> allStudents = new ArrayList<Student>(noOfStudents);

			for (int i = noOfStudents; i >= 1; i--) { // added in desc order form 11Smith to 01Smith

				Student s = new Student();
		    	s.setFirstName(this.student.getFirstName());
		    	s.setEmail(this.student.getEmail() + i);
		    	s.setPassword(this.student.getPassword());
		    	s.setTelephone(this.student.getTelephone());
		    	s.setBirthDate(this.student.getBirthDate());
		    	s.setAddress(this.student.getAddress());
		    	
				s.setLastName(String.format("%02d", i) + "Smith");
				if(i == noOfStudents) {
					leftOutStudentName = String.format("%03d", i) + "Smith";
				}

				allStudents.add(s); 
			}
						
			studentRepo.saveAll(allStudents);

			//when
			Collection<Student> foundAsc = studentRepo.findTop10By((new Sort(ASC, "lastName")));
			
			//then
			assertThat(foundAsc).hasSize(noOfStudentsToRetrieve);
			assertThat(foundAsc).first().extracting("lastName").containsExactly("01" + "Smith");
			assertThat(foundAsc).last().extracting("lastName").containsExactly( noOfStudentsToRetrieve + "Smith");
			
			for (int i = 1; i <= noOfStudents; i++) {
				assertThat( student.getLastName()).isNotEqualTo(leftOutStudentName); 
			}
		}
	    
	    @Test
		public void shouldFindByLastNameOrderByEmailAsc() {
	    	
	    	//given
			int noOfStudents = 11;
			int noOfStudentsExpected = 10;
			List<Student> allStudents = new ArrayList<Student>(noOfStudents);

			for (int i = noOfStudents; i >= 1; i--) { // added in desc order from 11@gmail.com to 01@gmail.com

				Student s = new Student();
		    	s.setFirstName(this.student.getFirstName());
		    	s.setPassword(this.student.getPassword());
		    	s.setTelephone(this.student.getTelephone());
		    	s.setBirthDate(this.student.getBirthDate());
		    	s.setAddress(this.student.getAddress());
		    	
		    	s.setLastName(this.student.getLastName());
		    	if(i == 11) {
					s.setLastName("DifferentName");
				}
		    	
				s.setEmail(String.format("%02d", i) + "@gmail.com");
				
				allStudents.add(s); 
			}
			
			studentRepo.saveAll(allStudents);
			
			//when
			
			// A slice of data that indicates whether there's a next or previous slice available. 
			//Allows to obtain a Pageable to request a previous or next Slice.
			Slice<Student> firstPage = studentRepo.findByLastNameOrderByEmailAsc(this.student.getLastName(), 
																						PageRequest.of(0, 5)); 
																			// Basic Java Bean implementation of Pageable.int page, int size
			Slice<Student> secondPage = studentRepo.findByLastNameOrderByEmailAsc(this.student.getLastName(), 
																						PageRequest.of(1, 6));
			
			//then
			assertThat(firstPage).first().extracting("email").containsExactly("01" + "@gmail.com");
			assertThat(secondPage).last().extracting("email").containsExactly(noOfStudentsExpected + "@gmail.com");
	    }
	    
	    @Test
		public void shouldFindStudentsBySchoolform_Id() {
	    	
	    	Schoolform firstForm = new Schoolform();
	    	firstForm.setName("First Form");
	    	schoolformRepo.save(firstForm);
	    	int expecteNoOfStudentInFirstForm = 2;
	    	
	    	Schoolform secondForm = new Schoolform();
	    	secondForm.setName("Second Form");
	    	schoolformRepo.save(secondForm);
	    	
	    	int noOfStudents = 10;
			for (int i = noOfStudents; i >= 1; i--) { // added in desc order 

				Student s = new Student();
		    	s.setFirstName(this.student.getFirstName());
		    	s.setPassword(this.student.getPassword());
		    	s.setTelephone(this.student.getTelephone());
		    	s.setBirthDate(this.student.getBirthDate());
		    	s.setAddress(this.student.getAddress());
				s.setLastName(this.student.getLastName());
				s.setEmail(this.student.getEmail() + i);
				
				if(i == 5 || i == 10) {
					s.setSchoolform(firstForm);
				} else {
					s.setSchoolform(secondForm);
				}
				
				studentRepo.save(s);
			}
			
			//when
			List<Student> firstFormStudents = (List<Student>) studentRepo.findBySchoolformId(firstForm.getId());
			List<Student> secondFormStudents = (List<Student>) studentRepo.findBySchoolformId(secondForm.getId());
			
			//
			assertThat(firstFormStudents).hasSize(expecteNoOfStudentInFirstForm);
			assertThat(secondFormStudents).hasSize(noOfStudents - expecteNoOfStudentInFirstForm);
			
	    }
		 @Test //TODO
		public void shouldThrowExceptionWhenSavingStudentWithNonUniqueEmail() {
			
				assertThatCode(() -> {
					//when
		    		studentRepo.save(this.student);
		    		//then
		    	}).doesNotThrowAnyException();
				
				//given
				Student differentEmail = new Student();
				differentEmail.setFirstName(this.student.getFirstName());
				differentEmail.setLastName(this.student.getLastName());
				differentEmail.setPassword(this.student.getPassword());
				differentEmail.setTelephone(this.student.getTelephone());
				differentEmail.setBirthDate(this.student.getBirthDate());
				differentEmail.setAddress(this.student.getAddress());
				differentEmail.setEmail(this.student.getEmail() + "foo");
				
				assertThatCode(() -> {
					//when
		    		studentRepo.save(differentEmail);
		    		//then
		    	}).doesNotThrowAnyException();
				
				//given
				Student sameEmail = new Student();
				sameEmail.setFirstName(this.student.getFirstName());
				sameEmail.setLastName(this.student.getLastName());
				sameEmail.setPassword(this.student.getPassword());
				sameEmail.setTelephone(this.student.getTelephone());
				sameEmail.setBirthDate(this.student.getBirthDate());
				sameEmail.setAddress(this.student.getAddress());
				sameEmail.setEmail(this.student.getEmail());
	    		    	
				//when
	    		Throwable thrown = catchThrowable(() -> { 
	    			studentRepo.save(sameEmail); 
	    		});
	    		
	    		// then
	    		assertThat(thrown).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
	    }
		
		@Test
		public void shouldNotReturnStudents_WhenIsDeletedIsTrue() {
			
			// given
			studentRepo.save(this.student);
			
			Student deleted = createStudent("Dexter");
			deleted.setDeleted(true);
			studentRepo.save(deleted);
			
			//then 
			Iterable<Student> list = studentRepo.findAll();
			assertThat(list).size().isEqualTo(1);
			
			
		}
		
		private Student createStudent(String name) {
			
			Student student = new Student();
			student = new Student();
			student.setFirstName(name);
			student.setLastName("Motgmomery");
			student.setEmail(name + "@gamil.com");
			student.setPassword("567");
			student.setTelephone("1234567");
			student.setBirthDate(LocalDate.of(2000, 1, 1));
			student.setAddress("Manchester, England");
			
			return student;
		}
		
		
	    	    
}
