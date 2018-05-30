package pl.ust.school.subject;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.repository.AppBaseRepository;

public interface SubjectRepository extends AppBaseRepository<Subject, Long>{
	
	@Transactional(readOnly = true)  
	Collection<Subject> findByName(String name);
	
	
}
