package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.TSSDto;

public interface TSSService {
	
	
	long createTSS(TSSDto tsDto);
	Collection<TSSDto> getAllTSSs();
	Optional<TSSDto> getTSSById(long id);
	void deleteTSS(long id);

	Collection<TSSDto> getTSSsBySchoolformId(long id);
	Collection<TSSDto> getTSSsByTeacherId(long id);
	Collection<TSSDto> getTSSsBySubjectId(long id);


}
