package pl.ust.school.student;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.tss.TSSDto;

public interface StudentService {
	 
	long createStudent(StudentDto studentDto);
	Collection<StudentDto> getAllStudents();
	Optional<StudentDto> getStudentDtoById(long id);
	void deleteStudent(long id);
	Collection<StudentDto> getStudentsBySchoolformId(long id);
	public void removeStudentFromSchoolform(long studentId);
	TSSDto filterGradesWithSubject(TSSDto tssDto);

}
