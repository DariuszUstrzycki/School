package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Controller;

import pl.ust.school.dto.SubjectDto;

public interface SubjectService {
	
	long createSubject(SubjectDto subjectDto);
	Collection<SubjectDto> getAllSubjects();
	Optional<SubjectDto> getSubjectById(Long id);
	void deleteSubject(Long id);
	
	
}