package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.SubjectDto;
import pl.ust.school.dto.TeacherDto;
import pl.ust.school.entity.TeacherSubject;

public interface TeacherService {
	
	long createTeacher(TeacherDto teacherDto);
	Collection<TeacherDto> getAllTeacherDtos();
	Optional<TeacherDto> getTeacherDtoById(Long id);
	void deleteTeacher(Long id);
	void removeTeacherSubject(long teacherId, long tssId);
	void addTeacherSubject(long teacherId, long subjectId);
	Collection<SubjectDto> getSubjectDtosNotTaughtByTeacher(TeacherDto teacherDto, Collection<SubjectDto> allSubjects);
	
	

}
