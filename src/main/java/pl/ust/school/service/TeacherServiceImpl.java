package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.TeacherDto;
import pl.ust.school.entity.Teacher;
import pl.ust.school.mapper.TeacherMapper;
import pl.ust.school.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	private TeacherRepository repo;

	@Autowired
	private TeacherMapper mapper;

	public long createTeacher(TeacherDto teacherDto) {
		Teacher teacher = this.mapper.fromDTO(teacherDto);
		teacher = this.repo.save(teacher);
		return teacher.getId();
	}

	public Collection<TeacherDto> getAllTeachers() {

		return this.repo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	public Optional<TeacherDto> getTeacherById(Long id) {

		return this.mapper.toDTO(repo.findById(id));
	}

	public void deleteTeacher(Long id) {

		Optional<Teacher> opt = this.repo.findById(id);
		opt.ifPresent(teacher -> {
			teacher.remove();
			this.repo.save(teacher);
		});
	}

}
