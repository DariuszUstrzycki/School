package pl.ust.school.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("hiding")
@NoRepositoryBean                                  
public interface AppBaseRepository<T, Long> extends CrudRepository<T , Long> {

	//from PagingAndSortingRepository 
	@Transactional(readOnly = true)  
	Iterable<T> findAll(Sort sort);
	
	@Transactional(readOnly = true) 
	Page<T>	findAll(Pageable pageable);
	
	@Transactional(readOnly = true) 
	Collection<T> findAll();
	
	@Transactional(readOnly = true) 
	long count();
	
	@Transactional 
	<S extends T> S save(S entity);
	
}
