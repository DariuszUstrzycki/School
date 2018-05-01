package pl.ust.school.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.entity.SchoolForm;

@SuppressWarnings("hiding")
@NoRepositoryBean                                  
public interface AppBaseRepository<T, Long> extends Repository<T, Long> {
	
	// add @Query("SELECT DISTINCT .......................") ?!
		
	<S extends SchoolForm> S save(S entity);
	
	@Transactional(readOnly = true)  
	Optional<T> findById(Long id);
	
	@Transactional(readOnly = true)  
	Iterable<T> findAll();
	
	@Transactional
	void deleteById(Long id);
		
	
	//from PagingAndSortingRepository 
	Iterable<T>	findAll(Sort sort);
	Page<T>	findAll(Pageable pageable);
	
}
