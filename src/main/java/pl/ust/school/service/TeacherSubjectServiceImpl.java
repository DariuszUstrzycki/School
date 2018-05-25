package pl.ust.school.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.entity.TeacherSubject;
import pl.ust.school.repository.TeacherSubjectRepository;

@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService{
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;

	@Override
	public Optional<TeacherSubject> getTeacherSubject(long teacherId, long subjectId) {
		
		return this.teacherSubjectRepo.findByTeacherIdAndSubjectId(teacherId, subjectId);
	}
	
	

}
