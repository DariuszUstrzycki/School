package pl.ust.school.repository;

import java.util.Collection;

import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.TSS;

public interface TSSRepository extends AppBaseRepository<TSS, Long> {
	
	Collection<TSS> findTSSBySchoolformId(long id);
	Collection<TSS> findTSSByTeacherId(long id);
	Collection<TSS> findTSSBySubjectId(long id);

}
