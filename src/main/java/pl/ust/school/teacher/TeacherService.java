package pl.ust.school.teacher;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.subject.SubjectDto;

public interface TeacherService {
	
	long createTeacher(TeacherDto teacherDto);
	Collection<TeacherDto> getAllTeacherDtos();
	Optional<TeacherDto> getTeacherDtoById(long id);
	Optional<Teacher> getTeacherById(long id);
	void deleteTeacher(long id);
	void removeTSS(long tSSId);
	void addTSS(long teacherId, long subjectId);
	Collection<SubjectDto> getNotTaughtSubjects(TeacherDto teacherDto);
	
	

}
