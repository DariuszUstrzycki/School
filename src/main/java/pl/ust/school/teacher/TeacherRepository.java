package pl.ust.school.teacher;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.repository.AppBaseRepository;

public interface TeacherRepository extends AppBaseRepository<Teacher, Long>{
	
	@Transactional(readOnly = true)
	@Query("select t from Teacher t where t.email = ?1")
	Optional<Teacher> findByEmail(String email); 
	
	@Transactional(readOnly = true)
	@Query("select t from Teacher t where t.lastName LIKE %?1%")
	Collection<Teacher> findByLastNameContains(String string); // Contains
	
	@Transactional(readOnly = true)
	Collection<Teacher> findByFirstNameAndLastName(String firstName, String lastName); 
	
	@Transactional(readOnly = true)
	Collection<Teacher> findByLastNameOrderByLastNameAsc(String lastName); 
	
	@Transactional(readOnly = true)
	Collection<Teacher> findTop10By(Sort sort);
	
	@Transactional(readOnly = true)
	Slice<Teacher> findByLastNameOrderByEmailAsc(String lastName, Pageable of);
	
	@Transactional(readOnly = true)
	Iterable<Teacher> saveAll(Iterable<Teacher> teachers);
	

}
