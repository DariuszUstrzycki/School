package pl.ust.school.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import pl.ust.school.entity.SchoolForm;

@SuppressWarnings("hiding")
@NoRepositoryBean                                  
public interface AppBaseRepository<T, Long> extends Repository<T, Long> {
		
	<S extends SchoolForm> S save(S entity);
	
	Optional<T> findById(Long id);
	
	void deleteById(Long id);
	
	Iterable<T> findAll();
	
	//from PagingAndSortingRepository 
	Iterable<T>	findAll(Sort sort);
	Page<T>	findAll(Pageable pageable);
	
}
