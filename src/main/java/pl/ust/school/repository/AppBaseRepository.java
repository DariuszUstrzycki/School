package pl.ust.school.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.entity.IdEntity;

@SuppressWarnings("hiding")
@NoRepositoryBean                                  
public interface AppBaseRepository<T, Long> extends Repository<T , Long> {
	
	// add @Query("SELECT DISTINCT .......................") ?!
		
	<T extends IdEntity> IdEntity save(T entity);
	
	@Transactional(readOnly = true)  
	Optional<? extends IdEntity> findById(Long id);
	
	@Transactional(readOnly = true)  
	Iterable<? extends IdEntity> findAll();
	 
	@Transactional
	void deleteById(Long id);
	
	
		
	
	//from PagingAndSortingRepository 
	Iterable<T>	findAll(Sort sort);
	Page<T>	findAll(Pageable pageable);
	
}
