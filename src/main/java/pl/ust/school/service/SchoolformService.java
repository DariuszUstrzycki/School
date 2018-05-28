package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.SchoolformDto;
import pl.ust.school.dto.TeacherSubjectDto;
import pl.ust.school.entity.Schoolform;

public interface SchoolformService {
	
	long createSchoolform(SchoolformDto subjectDto);
	Collection<SchoolformDto> getAllSchoolformDtos();
	Optional<SchoolformDto> getSchoolformDtoById(long id);
	Optional<Schoolform> getSchoolformById(long id);
	void deleteSchoolform(Long id);
	Collection<TeacherSubjectDto> getTeacherSubjectsNotTaughtInSchoolform(SchoolformDto dto, Collection<TeacherSubjectDto> allTSDtos);

}
