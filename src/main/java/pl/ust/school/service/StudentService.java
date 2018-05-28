package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.StudentDto;

public interface StudentService {
	 
	long createStudent(StudentDto studentDto);
	Collection<StudentDto> getAllStudents();
	Optional<StudentDto> getStudentDtoById(long id);
	void deleteStudent(long id);
	Collection<StudentDto> getStudentBySchoolformId(long id);
	public void removeStudentFromSchoolform(long studentId);

}
