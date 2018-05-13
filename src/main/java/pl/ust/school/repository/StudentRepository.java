package pl.ust.school.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import pl.ust.school.entity.Student;

public interface StudentRepository extends AppBaseRepository<Student, Long> {
	
	@Query("select s from Student s where s.email = ?1")
	Optional<Student> findByEmail(String email); 
		
	@Query("select s from Student s where s.lastName LIKE %?1%")
	Collection<Student> findByLastNameContains(String string); // Contains
	
	Collection<Student> findByFirstNameAndLastName(String firstName, String lastName); 
	Collection<Student> findByLastNameOrderByLastNameAsc(String lastName); 
	Collection<Student> findTop10By(Sort sort);
	Slice<Student> findByLastNameOrderByEmailAsc(String lastName, Pageable of);
	
	@Query("select s from Student s where s.schoolForm.id =	?1")
	Collection<Student> findBySchoolForm_Id(long id); // 1. _ nie jest tu konieczny. 2.Search by field SchoolForm in
														// Student

	
	
	
	
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
		// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
		
		// --------------NATIVE QUERIES-------------------
			// @Query(value	=	"SELECT	* FROM STUDENTS	WHERE ADDRESS = ?1", nativeQuery = true)
			
			
			
		// parameters ORDER -------------------------------------	
	
	
	

	/*
	
	@Query("select s from Student s where s.lastName LIKE ?1%")
	Collection<Student> findByLastNameStartingWith(); // StartingWith

	
	Collection<Student> findByLastNameNot(String string); // Not

	// parameters NAMING -------------------------------------	
	
	@Query("select s from Student s where s.firstName =	:first OR s.lastName = :second")
	Collection<Student> findByFirstNameAndLastNameAllIgnoreCaseOrderByLastNameAsc(@Param("first") String firstName, 
																					@Param("second") String lastName);
	
	Collection<Student> findBySchoolFormIsNull(); // isNull
	Collection<Student> findBySchoolFormNameIgnoreCase(String schoolformName); 
	Collection<Student> findByLastNameIgnoreCaseOrderByLastNameAsc(String lastName); // findBy...IgnoreCaseOrderBy...Asc
	Collection<Student> findByBirthDate(LocalDate birthDate);
	Collection<Student> findByBirthDateBefore(LocalDate date); // Before After
	
	long countBySchoolFormName(String firstName);
	long countBySchoolFormId(String firstName);
	
	*/
	
	
	
	
	// ----------search in a collection------------------
	//Collection<Student> findByGradeIn(Collection<Grade> grades);
	// In and NotIn also take any subclass of Collection as parameter as well as arrays or varargs.
	
	//Collection<Student> findByActiveTrue();
	//Collection<Student> findByisDeletedFalse();
	
	
}
