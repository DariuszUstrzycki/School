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
public interface AppBaseRepository<T, Long> extends Repository<T , Long> {
	
	@Transactional
	T save(T entity);
	
	@Transactional(readOnly = true)  
	Optional<T> findById(Long id);
	
	@Transactional(readOnly = true)  
	Iterable<T> findAll();
	 
	@Transactional
	void deleteById(Long id);
	
	//from PagingAndSortingRepository 
	@Transactional(readOnly = true)  
	Iterable<T> findAll(Sort sort);
	
	@Transactional(readOnly = true) 
	Page<T>	findAll(Pageable pageable);
	
}
