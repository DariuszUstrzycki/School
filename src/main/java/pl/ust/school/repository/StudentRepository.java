package pl.ust.school.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.ust.school.entity.Student;

public interface StudentRepository extends AppBaseRepository<Student, Long> {
	
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
	
	// --------------NATIVE QUERIES-------------------
		@Query(value	=	"SELECT	* FROM STUDENTS	WHERE ADDRESS = ?1", nativeQuery = true)
		Collection<Student> findByLastNameNot(String string); // Not
		
	// parameters ORDER -------------------------------------	
		
	@Query("select s from Student s where s.lastName LIKE %?1%")
	Collection<Student> findByLastNameContains(String string); // Contains

	@Query("select s from Student s where s.lastName LIKE ?1%")
	Collection<Student> findByLastNameStartingWith(); // StartingWith

	@Query("select s from Student s where s.schoolForm.id =	?1")
	Collection<Student> findBySchoolForm_Id(long id); // 1. _ nie jest tu konieczny. 2.Search by field SchoolForm in
														// Student

	// parameters NAMING -------------------------------------	
	
	@Query("select s from Student s where s.firstName =	:first or u.lastName = :second")
	Collection<Student> findByFirstNameAndLastNameAllIgnoreCaseOrderByLastNameAsc(@Param("first") String firstName, 
																					@Param("second") String lastName);
	
	Collection<Student> findBySchoolFormIsNull(); // isNull
	Collection<Student> findBySchoolFormNameIgnoreCase(String schoolformName); 
	Collection<Student> findByLastNameIgnoreCaseOrderByLastNameAsc(String lastName); // findBy...IgnoreCaseOrderBy...Asc
	Collection<Student> findByBirthDate(LocalDate birthDate);
	Collection<Student> findByBirthDateBefore(LocalDate date); // Before After
	
	long countBySchoolFormName(String firstName);
	long countBySchoolFormId(String firstName);
	
	// ----------search in a collection------------------
	//Collection<Student> findByGradeIn(Collection<Grade> grades);
	// In and NotIn also take any subclass of Collection as parameter as well as arrays or varargs.
	
	//Collection<Student> findByActiveTrue();
	//Collection<Student> findByisDeletedFalse();
	
	
}
