package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.SubjectDto;
import pl.ust.school.entity.Subject;

public interface SubjectService {
	
	long createSubject(SubjectDto subjectDto);
	Collection<SubjectDto> getAllSubjectDtos();
	Optional<SubjectDto> getSubjectDtoById(Long id);
	public Optional<Subject> getSubjectById(Long id);
	
	
}