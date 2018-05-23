package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;

import pl.ust.school.dto.TeacherSubjectSchformDto;

public class TeacherSubjectSchformImpl implements TeacherSubjectSchform  {

	@Override
	public long createTSS(TeacherSubjectSchformDto tssDto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<TeacherSubjectSchformDto> getAllTSSs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TeacherSubjectSchformDto> getTSSById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TeacherSubjectSchformDto> getTSSsByTeacher(long teacherId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TeacherSubjectSchformDto> getTSSsBySubject(long teacherId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TeacherSubjectSchformDto> getTSSsBySchoolform(long teacherId) {
		// TODO Auto-generated method stub
		return null;
	}

}
