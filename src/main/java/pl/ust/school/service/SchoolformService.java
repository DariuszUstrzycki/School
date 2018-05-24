package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.SchoolformDto;

public interface SchoolformService {
	
	long createSchoolform(SchoolformDto subjectDto);
	Collection<SchoolformDto> getAllSchoolforms();
	Optional<SchoolformDto> getSchoolformById(Long id);
	void deleteSchoolform(Long id);

}
