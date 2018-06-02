package pl.ust.school.tss;

import java.util.Collection;
import java.util.Optional;

public interface TSSService {
	
	Optional<TSS> getTSS(long teacherId, long subjectId);
	Optional<TSS> getTSS(long tSSId);
	TSSDto getTSSDto(long tSSId);
	Collection<TSS> getAllTSSs();
	Collection<TSSDto> getAllTSSDtos(); 
	void deleteTSS(long tSSId);
	void deleteTSSsBySubject(long subjectId);

}
