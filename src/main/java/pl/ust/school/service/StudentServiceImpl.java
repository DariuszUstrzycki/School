package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.StudentDto;
import pl.ust.school.entity.Student;
import pl.ust.school.mapper.StudentMapper;
import pl.ust.school.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repo;

	@Autowired
	private StudentMapper mapper;

	public long createStudent(StudentDto studentDto) {
		Student student = this.mapper.fromDTO(studentDto);
		student = this.repo.save(student);
		return student.getId();
	}

	public Collection<StudentDto> getAllStudents() {

		return this.repo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	public Optional<StudentDto> getStudentById(Long id) {

		return this.mapper.toDTO(repo.findById(id));
	}

	public void deleteStudent(Long id) {

		Optional<Student> opt = this.repo.findById(id);
		opt.ifPresent(student -> {
			student.remove();
			this.repo.save(student);
		});
	}

	@Override
	public Collection<StudentDto> getStudentBySchoolform_Id(long id) {
		
		return this.repo.findBySchoolform_Id(id)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toSet());
	}
	
	

}
