package pl.ust.school.subject;

import java.util.Collection;
import java.util.Optional;

public interface SubjectService {
	
	long createSubject(SubjectDto subjectDto);
	Collection<SubjectDto> getAllSubjectDtos();
	Optional<SubjectDto> getSubjectDtoById(long id);
	public Optional<Subject> getSubjectById(long id);
	void deleteSubject(long id);
	
	
}