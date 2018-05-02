package pl.ust.school.repository;

import java.util.Collection;

import pl.ust.school.entity.SchoolForm;

public interface SchoolFormRepository extends AppBaseRepository<SchoolForm, Long> {
	
	Collection<SchoolForm> findByName(String name);
	
}
