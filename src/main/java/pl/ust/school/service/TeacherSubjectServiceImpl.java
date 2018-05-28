package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.TeacherSubjectDto;
import pl.ust.school.entity.TeacherSubject;
import pl.ust.school.mapper.TeacherSubjectMapper;
import pl.ust.school.repository.TeacherSubjectRepository;

@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService{
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;
	
	@Autowired
	private TeacherSubjectMapper teacherSubjectMapper;

	@Override
	public Optional<TeacherSubject> getTeacherSubject(long teacherId, long subjectId) {
		return this.teacherSubjectRepo.findByTeacherIdAndSubjectId(teacherId, subjectId);
	}

	@Override
	public void delete(long teacherSubjectId) {
		this.teacherSubjectRepo.deleteById(teacherSubjectId);
	}

	@Override
	public Collection<TeacherSubject> getAllTeacherSubjects() {
		return this.teacherSubjectRepo.findAll();
	}

	@Override
	public Collection<TeacherSubjectDto> getAllTeacherSubjectDtos() {
		return this.teacherSubjectRepo.findAll()
				.stream()
				.map(teacherSubjectMapper::toDTO)
				.collect(Collectors.toSet());
	}

}
