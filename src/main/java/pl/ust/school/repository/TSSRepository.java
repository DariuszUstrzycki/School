package pl.ust.school.repository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import pl.ust.school.entity.TSS;

public interface TSSRepository extends AppBaseRepository<TSS, Long>{
	
	@Transactional(readOnly = true)
	Optional<TSS> findByTeacherIdAndSubjectId(long teacherId, long subjectId);
	
}
