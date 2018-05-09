package pl.ust.school.repository;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.entity.SchoolForm;

public interface SchoolFormRepository extends AppBaseRepository<SchoolForm, Long> {
	
	@Transactional(readOnly = true)  
	Collection<SchoolForm> findByName(String name);
	
	
}
