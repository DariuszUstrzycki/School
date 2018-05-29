package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.Subject;
import pl.ust.school.entity.TSS;

public interface TSSService {
	
	Optional<TSS> getTSS(long teacherId, long subjectId);
	Optional<TSS> getTSS(long tSSId);
	Collection<TSS> getAllTSSs();
	Collection<TSSDto> getAllTSSDtos(); 
	void deleteTSS(long tSSId);
	void deleteTSSsBySubject(long subjectId);

}
