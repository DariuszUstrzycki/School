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
import pl.ust.school.entity.TSS;
import pl.ust.school.mapper.TeacherMapper;
import pl.ust.school.repository.SubjectRepository;
import pl.ust.school.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private SubjectRepository subjectRepo;

	@Autowired
	private TeacherMapper mapper;

	public long createTeacher(TeacherDto teacherDto) {
		Teacher teacher = this.mapper.fromDTO(teacherDto);
		System.err.println("---------------3.Teacher subjects after mapping to teacher: " + teacher.getTSSs().size());
		
		teacher = this.teacherRepo.save(teacher);
		System.err.println("----------------7.Teacher subjects after saving the teacher: " + teacher.getTSSs().size());
		return teacher.getId();
	}

	public Collection<TeacherDto> getAllTeachers() {

		return this.teacherRepo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	public Optional<TeacherDto> getTeacherById(Long id) {

		return this.mapper.toDTO(teacherRepo.findById(id));
	}

	public void deleteTeacher(Long id) {

		Optional<Teacher> opt = this.teacherRepo.findById(id);
		opt.ifPresent(teacher -> {
			teacher.remove();
			this.teacherRepo.save(teacher);
		});
	}

	@Override
	public void removeTSS(long teacherId, long tssId) {
		Optional<Teacher> opt = this.teacherRepo.findById(teacherId);
		opt.ifPresent(teacher -> {
			teacher.removeTSS(tssId);
			this.teacherRepo.save(teacher);
		});
		
	}
	
	@Override
	public Collection<SubjectDto> getSubjectsNotTaughtByTeacher(TeacherDto teacherDto, Collection<SubjectDto> allSubjects) {
		
		for(TSS ts : teacherDto.getTSSs()) {
			allSubjects.removeIf(subject -> subject.getName().equals(ts.getSubject().getName()));
		}
		
		return allSubjects;
		
	}

	@Override
	public void addTSS(long teacherId, long subjectId) {
		
		TSS ts = new TSS();
		Teacher teacher;
		
		Optional<Teacher> teacherOpt = this.teacherRepo.findById(teacherId);
		if(teacherOpt.isPresent()) {
			teacher = teacherOpt.get();
			ts.setTeacher(teacher);
		} else {
			throw new RecordNotFoundException("No teacher with id " + teacherId + " has been found.");
		}
		
		Optional<Subject> subjectOpt = this.subjectRepo.findById(subjectId);
		if(subjectOpt.isPresent()) {
			ts.setSubject(subjectOpt.get());
		} else {
			throw new RecordNotFoundException("No subject with id " + subjectId + " has been found.");
		}
		
		System.err.println("******************Saving teacher........");
		
		teacher.addTSS(ts);
		this.teacherRepo.save(teacher);
		
	}


}
