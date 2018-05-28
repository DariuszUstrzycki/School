package pl.ust.school.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("hiding")
@NoRepositoryBean                                  
public interface AppBaseRepository<T, Long> extends Repository<T, Long> {
	
	@Transactional 
	<S extends T> S save(S entity);
	
	@Transactional
	void delete(T entity);
	
	@Transactional
	void deleteById(long id);
	
	Optional<T>	findById(Long id);
	
	//from PagingAndSortingRepository 
	@Transactional(readOnly = true)  
	Iterable<T> findAll(Sort sort);
	
	@Transactional(readOnly = true) 
	Page<T>	findAll(Pageable pageable);
	
	@Transactional(readOnly = true) 
	Collection<T> findAll();
	
	@Transactional(readOnly = true) 
	long count();
	
}
