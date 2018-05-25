package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.controller.RecordNotFoundException;
import pl.ust.school.dto.SubjectDto;
import pl.ust.school.dto.TeacherDto;
import pl.ust.school.entity.Subject;
import pl.ust.school.entity.Teacher;
import pl.ust.school.entity.TeacherSubject;
import pl.ust.school.mapper.TeacherMapper;
import pl.ust.school.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private TeacherSubjectService teacherSubjectService;

	@Autowired
	private TeacherMapper mapper;

	@Override
	public long createTeacher(TeacherDto teacherDto) {
		Teacher teacher = this.mapper.fromDTO(teacherDto);
		teacher = this.teacherRepo.save(teacher);
		return teacher.getId();
	}

	@Override
	public Collection<TeacherDto> getAllTeacherDtos() {

		return this.teacherRepo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<TeacherDto> getTeacherDtoById(Long id) {
		return this.mapper.toDTO(teacherRepo.findById(id));
	}

	@Override
	public void deleteTeacher(Long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);
		opt.ifPresent(teacher -> {
			teacher.remove();
			this.teacherRepo.save(teacher);
		});
	}

	@Override
	public Collection<SubjectDto> getSubjectDtosNotTaughtByTeacher(TeacherDto teacherDto,
			Collection<SubjectDto> allSubjects) {

		for (TeacherSubject teacherSubject : teacherDto.getSubjects()) {
			allSubjects.removeIf(subject -> subject.getName().equals(teacherSubject.getSubject().getName()));
		}

		return allSubjects;

	}

	@Override
	public void removeTeacherSubject(long teacherId, long subjectId) {
		Optional<Teacher> teacherOpt = this.teacherRepo.findById(teacherId);
		Optional<Subject> subjectOpt = this.subjectService.getSubjectById(subjectId);

		if (teacherOpt.isPresent() && subjectOpt.isPresent()) {
			Teacher teacher = teacherOpt.get();
			teacher.removeSubject(subjectOpt.get());
			this.teacherRepo.save(teacher);
		}
	}

	@Override
	public void addTeacherSubject(long teacherId, long subjectId) {

		if (!isUniqueTeacherSubject(teacherId, subjectId)) {
			return;
		}

		Teacher teacher;

		Optional<Teacher> teacherOpt = this.teacherRepo.findById(teacherId);
		if (teacherOpt.isPresent()) {
			teacher = teacherOpt.get();
		} else {
			throw new RecordNotFoundException("No teacher with id " + teacherId + " has been found.");
		}

		Optional<Subject> subjectOpt = this.subjectService.getSubjectById(subjectId);
		if (subjectOpt.isPresent()) {
			Subject subject = subjectOpt.get();
			teacher.addSubject(subject);
		} else {
			throw new RecordNotFoundException("No subject with id " + subjectId + " has been found.");
		}

		this.teacherRepo.save(teacher);
	}

	private boolean isUniqueTeacherSubject(long teacherId, long subjectId) {

		Optional<TeacherSubject> opt = this.teacherSubjectService.getTeacherSubject(teacherId, subjectId);
		return opt.isPresent() ? false : true;

	}

}
