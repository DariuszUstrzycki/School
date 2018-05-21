package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.TeacherDto;

public interface TeacherService {
	
	long createTeacher(TeacherDto teacherDto);
	Collection<TeacherDto> getAllTeachers();
	Optional<TeacherDto> getTeacherById(Long id);
	void deleteTeacher(Long id);

}
