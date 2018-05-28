package pl.ust.school.repository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import pl.ust.school.entity.TeacherSubject;

public interface TeacherSubjectRepository extends AppBaseRepository<TeacherSubject, Long>{
	
	@Transactional(readOnly = true)
	Optional<TeacherSubject> findByTeacherIdAndSubjectId(long teacherId, long subjectId);
	
}
