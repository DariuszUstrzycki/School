package pl.ust.school.student;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.grade.Grade;
import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.schoolform.SchoolformService;
import pl.ust.school.system.RecordNotFoundException;
import pl.ust.school.tss.TSSDto;

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
			student.setDeleted(true);
			//student.remove(); - nic wiecej nie robi...
			this.studentRepo.save(student);
		} else {
			throw new RecordNotFoundException("No student with id " + id + " has been found.");
		}
	}

	@Override
	public Collection<StudentDto> getStudentsBySchoolformId(long id) {

		return this.studentRepo.findBySchoolformId(id).stream().map(mapper::toDTO).collect(Collectors.toSet());
	}

	@Override
	public TSSDto filterGradesWithSubject(TSSDto tssDto) {
		
		long subjectId = tssDto.getSubject().getId();
		Collection<Student> students = tssDto.getSchoolform().getStudents();
		
		for (Student student : students) {
			
			for (Iterator iterator = student.getGrades().iterator(); iterator.hasNext();) {
				Grade grade = (Grade) iterator.next();
				if(grade.getSubject().getId() != subjectId) {
					iterator.remove();
				}
				
			}
		}
		
		return tssDto;
	}

}
