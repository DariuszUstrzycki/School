package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.controller.RecordNotFoundException;
import pl.ust.school.dto.StudentDto;
import pl.ust.school.entity.Schoolform;
import pl.ust.school.entity.Student;
import pl.ust.school.mapper.StudentMapper;
import pl.ust.school.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private StudentMapper mapper;

	@Autowired
	private SchoolformService schoolformService;

	///////////////////////////////////
	@Override
	public void removeStudentFromSchoolform(long studentId) {

		Optional<Student> opt = this.studentRepo.findById(studentId);
		if (opt.isPresent()) {
			Student student = opt.get();
			student.getSchoolform().removeStudent(student);
			this.studentRepo.save(student);
		} else {
			throw new RecordNotFoundException("No student with id " + studentId + " has been found.");
		}
	}

	////////////////////////////

	private void addStudentToSchoolform(Student student) {

		Schoolform schoolform;
		long schoolformId = student.getSchoolform().getId();
		Optional<Schoolform> schoolformOpt = this.schoolformService.getSchoolformById(schoolformId);
		if (schoolformOpt.isPresent()) {
			schoolform = schoolformOpt.get();
			schoolform.addStudent(student);
		} else {
			throw new RecordNotFoundException("No school form with id " + schoolformId + " has been found.");
		}
	}

	////////////////////////////

	@Override
	public long createStudent(StudentDto studentDto) {

		Student student = this.mapper.fromDTO(studentDto);

		if (student.isNew()) { // update to non-new student's schoolform is added on student save
			addStudentToSchoolform(student);
		}

		student = this.studentRepo.save(student);
		return student.getId();
	}

	@Override
	public Collection<StudentDto> getAllStudents() {

		return this.studentRepo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<StudentDto> getStudentDtoById(long id) {
		Optional<Student> opt = this.studentRepo.findById(id);

		if (opt.isPresent()) {
			return this.mapper.toDTO(opt);
		} else {
			throw new RecordNotFoundException("No student with id " + id + " has been found.");
		}
	}

	@Override
	public void deleteStudent(long id) {

		Optional<Student> opt = this.studentRepo.findById(id);
		if (opt.isPresent()) {
			Student student = opt.get();
			student.remove();
			this.studentRepo.save(student);
		} else {
			throw new RecordNotFoundException("No student with id " + id + " has been found.");
		}
	}

	@Override
	public Collection<StudentDto> getStudentBySchoolformId(long id) {

		return this.studentRepo.findBySchoolformId(id).stream().map(mapper::toDTO).collect(Collectors.toSet());
	}

}
