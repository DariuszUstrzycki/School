package pl.ust.school.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.entity.Subject;

public interface SubjectRepository extends AppBaseRepository<Subject, Long>{
	
	@Transactional(readOnly = true)  
	Collection<Subject> findByName(String name);
	
	
}
