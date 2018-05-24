package pl.ust.school.repository;

import java.util.Collection;

import pl.ust.school.entity.TSS;

public interface TSSRepository extends AppBaseRepository<TSS, Long> {
	
	Collection<TSS> findByOrderBySchoolformAsc();
	
	Collection<TSS> findTSSBySchoolformIdOrderBySubjectAsc(long id);
	Collection<TSS> findTSSByTeacherIdOrderBySubjectAsc(long id);
	Collection<TSS> findTSSBySubjectIdOrderByTeacherAsc(long id);

}
