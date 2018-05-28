package pl.ust.school.service;

import java.util.Optional;

import pl.ust.school.entity.TeacherSubject;

public interface TeacherSubjectService {
	
	Optional<TeacherSubject> getTeacherSubject(long teacherId, long subjectId);
	public void delete(long teacherSubjectId);

}
