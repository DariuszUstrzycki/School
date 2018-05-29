package pl.ust.school.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.entity.Schoolform;

public interface SchoolformRepository extends AppBaseRepository<Schoolform, Long> {
	
	@Transactional(readOnly = true)  
	Collection<Schoolform> findByName(String name);
	Optional<Schoolform> findByIdAndTssesId(long schoolformId, long tSSId);

	
	
}
