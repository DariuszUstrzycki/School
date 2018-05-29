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

	///////////////////////////////////////////////////

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
	public Optional<TeacherDto> getTeacherDtoById(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);

		if (opt.isPresent()) {
			return this.mapper.toDTO(opt);
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}
	}

	@Override
	public void deleteTeacher(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);
		if (opt.isPresent()) {
			Teacher teacher = opt.get();
			teacher.removeFromAllTeacherSubjects();
			teacher.setDeleted(true);
			this.teacherRepo.save(teacher);
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}
	}

	@Override
	public Collection<SubjectDto> getNotTaughtSubjects(TeacherDto teacherDto) {
		
		Collection<SubjectDto> all = this.subjectService.getAllSubjectDtos();

		for (TeacherSubject teacherSubject : teacherDto.getSubjects()) {
			all.removeIf(subject -> subject.getName().equals(teacherSubject.getSubject().getName()));
		}

		return all;

	}

	@Override
	public void removeTeacherSubject(long teacherSubjectId) {
		
		Optional<TeacherSubject> opt = this.teacherSubjectService.getTeacherSubject(teacherSubjectId);
		System.err.println("--------- teacherSubjectOpt:" + opt.get());

		if (opt.isPresent()) {
			TeacherSubject ts = opt.get();
			Teacher teacher = ts.getTeacher();
			//teacher.removeSubject(ts);
			this.teacherRepo.save(teacher);
		} else {
			throw new RecordNotFoundException("No teacher with id " + "XXXXXXXXXXXXXX" + " has been found.");
		}
	}

	@Override
	public void addTeacherSubject(long teacherId, long subjectId) {

		if (!isUniqueTeacherSubject(teacherId, subjectId)) {
			return;
		}

		Optional<Subject> subjectOpt = this.subjectService.getSubjectById(subjectId);
		Subject subject = subjectOpt.get();

		Optional<Teacher> teacherOpt = this.teacherRepo.findById(teacherId);
		if (teacherOpt.isPresent()) {
			Teacher teacher = teacherOpt.get();
			teacher.addSubject(subject);
			this.teacherRepo.save(teacher);
		} else {
			throw new RecordNotFoundException("No teacher with id " + teacherId + " has been found.");
		}

	}

	@Override
	public Optional<Teacher> getTeacherById(long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);

		if (opt.isPresent()) {
			return opt;
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}

	}

	private boolean isUniqueTeacherSubject(long teacherId, long subjectId) {

		Optional<TeacherSubject> opt = this.teacherSubjectService.getTeacherSubject(teacherId, subjectId);
		return opt.isPresent() ? false : true;

	}

}
