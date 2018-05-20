package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.SchoolFormDto;

public interface SchoolFormService {
	
	void createSchoolForm(SchoolFormDto subjectDto);
	Collection<SchoolFormDto> getAllSchoolForms();
	Optional<SchoolFormDto> getSchoolFormById(Long id);
	void deleteSchoolForm(Long id);

}
