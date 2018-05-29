package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.TeacherSubjectDto;
import pl.ust.school.entity.TeacherSubject;

public interface TeacherSubjectService {
	
	Optional<TeacherSubject> getTeacherSubject(long teacherId, long subjectId);
	Optional<TeacherSubject> getTeacherSubject(long teacherSubjectId);
	public void delete(long teacherSubjectId);
	Collection<TeacherSubject> getAllTeacherSubjects();
	Collection<TeacherSubjectDto> getAllTeacherSubjectDtos(); 

}
