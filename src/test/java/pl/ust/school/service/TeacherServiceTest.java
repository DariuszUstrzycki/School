package pl.ust.school.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.dto.TeacherDto;
import pl.ust.school.entity.Subject;
import pl.ust.school.entity.Teacher;
import pl.ust.school.mapper.TeacherMapper;

@RunWith(SpringRunner.class)
@DataJpaTest 
public class TeacherServiceTest {
	/*
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private TeacherMapper teacherMapper;*/
	
	private Teacher teacher;
	 
	@Before
	public void setUp() {
		teacher = createTeacher("Alex");
	}
	
	private Teacher createTeacher(String name) {
		Teacher teacher = new Teacher();
		teacher = new Teacher();
		teacher.setFirstName(name);
		teacher.setLastName("Motgmomery");
		teacher.setEmail(name + "@gamil.com");
		teacher.setPassword("567");
		teacher.setTelephone("1234567");
		teacher.setBirthDate(LocalDate.of(2000, 1, 1));
		teacher.setAddress("Manchester, England");
		return teacher;
	}
	
	@Test
	public void shouldAddTSS() {
		/*// given
		System.err.println(teacherMapper);
		TeacherDto t = teacherMapper.toDTO(this.teacher);
		this.teacherService.createTeacher(t);
		Subject subject = new Subject();
		subject.setId(1L);
		subject.setName("Maths");
		
		//when
		this.teacherService.addTSS(this.teacher.getId(), subject.getId());
		
		//then
		//TSSService tss;
		Optional<Teacher> opt = this.teacherService.getTeacherById(this.teacher.getId());
		Teacher found = opt.get();
		assertThat(found.getSubjects()).hasSize(1);
		assertThat(found.getSubjects()).first().isEqualTo(subject);*/
	}

}
