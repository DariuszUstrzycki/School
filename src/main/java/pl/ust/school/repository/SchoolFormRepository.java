package pl.ust.school.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.ust.school.entity.SchoolForm;

@Repository
public interface SchoolFormRepository extends CrudRepository<SchoolForm, Long> {
	
}
