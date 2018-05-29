package pl.ust.school.repository;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.entity.Schoolform;
import pl.ust.school.entity.TeacherSubject;

public interface SchoolformRepository extends AppBaseRepository<Schoolform, Long> {
	
	@Transactional(readOnly = true)  
	Collection<Schoolform> findByName(String name);

	
	
}
