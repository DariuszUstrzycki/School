package pl.ust.school.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import pl.ust.school.entity.Teacher;
import pl.ust.school.entity.TeacherSubject;

public interface TeacherRepository extends AppBaseRepository<Teacher, Long>{
	
	@Query("select t from Teacher t where t.email = ?1")
	Optional<Teacher> findByEmail(String email); 
		
	@Query("select t from Teacher t where t.lastName LIKE %?1%")
	Collection<Teacher> findByLastNameContains(String string); // Contains
	
	Collection<Teacher> findByFirstNameAndLastName(String firstName, String lastName); 
	Collection<Teacher> findByLastNameOrderByLastNameAsc(String lastName); 
	Collection<Teacher> findTop10By(Sort sort);
	Slice<Teacher> findByLastNameOrderByEmailAsc(String lastName, Pageable of);
	

}
