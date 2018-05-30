package pl.ust.school.schoolform;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.tss.TSSDto;

public interface SchoolformService {
	
	long createSchoolform(SchoolformDto subjectDto);
	Collection<SchoolformDto> getAllSchoolformDtos();
	Optional<SchoolformDto> getSchoolformDtoById(long id);
	Optional<Schoolform> getSchoolformById(long id);
	void deleteSchoolform(Long id);
	Collection<TSSDto> getNotTaughtTSSs(SchoolformDto dto);
	void removeSchoolformFromTSS(long schoolformId, long tSSId);
	void addSchoolformToTSS(long schoolformId, long tSSId);
	
	

}
