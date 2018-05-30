package pl.ust.school.tss;

import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.repository.AppBaseRepository;

public interface TSSRepository extends AppBaseRepository<TSS, Long>{
	
	@Transactional(readOnly = true)
	Optional<TSS> findByTeacherIdAndSubjectId(long teacherId, long subjectId);
	Collection<TSS> findBySubjectId(long subjectId);
	
}
