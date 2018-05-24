package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.StudentDto;

public interface StudentService {
	 
	long createStudent(StudentDto studentDto);
	Collection<StudentDto> getAllStudents();
	Optional<StudentDto> getStudentById(Long id);
	void deleteStudent(Long id);
	Collection<StudentDto> getStudentBySchoolform_Id(long id);

}
