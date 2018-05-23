package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.TeacherSubjectSchformDto;

public interface TeacherSubjectSchform {
	
	long createTSS(TeacherSubjectSchformDto tssDto);
	//void deleteTSS(long id);
	
	Collection<TeacherSubjectSchformDto> getAllTSSs();
	Optional<TeacherSubjectSchformDto> getTSSById(long id);
	Collection<TeacherSubjectSchformDto> getTSSsByTeacher(long teacherId);
	Collection<TeacherSubjectSchformDto> getTSSsBySubject(long teacherId);
	Collection<TeacherSubjectSchformDto> getTSSsBySchoolform(long teacherId);


}
