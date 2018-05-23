package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.SubjectDto;
import pl.ust.school.dto.TeacherDto;

public interface TeacherService {
	
	long createTeacher(TeacherDto teacherDto);
	Collection<TeacherDto> getAllTeachers();
	Optional<TeacherDto> getTeacherById(Long id);
	void deleteTeacher(Long id);
	void removeTeacherSubject(long teacherId, long teacherSubjectId);
	void addTeacherSubject(long teacherId, long subjectId);
	Collection<SubjectDto> getSubjectsNotTaughtByTeacher(TeacherDto teacherDto, Collection<SubjectDto> allSubjects);

}
