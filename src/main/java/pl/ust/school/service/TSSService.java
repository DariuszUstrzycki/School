package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.TSS;

public interface TSSService {
	
	Optional<TSS> getTSS(long teacherId, long subjectId);
	Optional<TSS> getTSS(long tSSId);
	public void delete(long tSSId);
	Collection<TSS> getAllTSSs();
	Collection<TSSDto> getAllTSSDtos(); 

}
