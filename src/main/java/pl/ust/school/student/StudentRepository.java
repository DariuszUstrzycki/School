package pl.ust.school.student;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.repository.AppBaseRepository;

public interface StudentRepository extends AppBaseRepository<Student, Long> {
	
	@Transactional(readOnly = true)
	@Query("select s from Student s where s.email = ?1")
	Optional<Student> findByEmail(String email); 
	
	@Transactional(readOnly = true)
	@Query("select s from Student s where s.lastName LIKE %?1%")
	Collection<Student> findByLastNameContains(String string); // Contains
	
	@Transactional(readOnly = true)
	Collection<Student> findByFirstNameAndLastName(String firstName, String lastName); 
	
	@Transactional(readOnly = true)
	Collection<Student> findByLastNameOrderByLastNameAsc(String lastName); 
	
	@Transactional(readOnly = true)
	Collection<Student> findTop10By(Sort sort);
	
	@Transactional(readOnly = true)
	Slice<Student> findByLastNameOrderByEmailAsc(String lastName, Pageable of);
	
	@Transactional(readOnly = true)
	@Query("select s from Student s where s.schoolform.id =	?1")
	Collection<Student> findBySchoolformId(long id); // 1. _ nie jest tu konieczny. 2.Search by field Schoolform in
														// Student
	@Transactional(readOnly = true)
	Iterable<Student> saveAll(Iterable<Student> students);

	
	
	
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
	
	Collection<Student> findBySchoolformIsNull(); // isNull
	Collection<Student> findBySchoolformNameIgnoreCase(String schoolformName); 
	Collection<Student> findByLastNameIgnoreCaseOrderByLastNameAsc(String lastName); // findBy...IgnoreCaseOrderBy...Asc
	Collection<Student> findByBirthDate(LocalDate birthDate);
	Collection<Student> findByBirthDateBefore(LocalDate date); // Before After
	
	long countBySchoolformName(String firstName);
	long countBySchoolformId(String firstName);
	
	*/
	
	
	
	
	// ----------search in a collection------------------
	//Collection<Student> findByGradeIn(Collection<Grade> grades);
	// In and NotIn also take any subclass of Collection as parameter as well as arrays or varargs.
	
	//Collection<Student> findByActiveTrue();
	//Collection<Student> findByisDeletedFalse();
	
	
}
