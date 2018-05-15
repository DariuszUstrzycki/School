package pl.ust.school.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.entity.Teacher;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TeacherJPATest {

	@Autowired
	private TestEntityManager tem;

	private Teacher teacher;

	@Before
	public void setUp() {

		teacher = new Teacher();
		teacher.setFirstName("Mike");
		teacher.setLastName("SmithXYZ");
		teacher.setEmail("smithXYZ@gamil.com");
		teacher.setPassword("567");
		teacher.setTelephone("1234567");
		teacher.setBirthDate(LocalDate.of(2000, 1, 1));
		teacher.setAddress("Manchester, England");
	}

	@Test
	public void mapping() {

		Teacher persisted = this.tem.persistAndFlush(this.teacher);
		assertThat(persisted.getLastName()).isEqualTo(this.teacher.getLastName());
		assertThat(persisted.getId()).isNotNull();
		assertThat(persisted.getId()).isGreaterThan(0);

		/*
		 * Other useful methods Long id = (Long) this.tem.getId(persisted); Teacher
		 * found = this.tem.find(Teacher.class, 1L); Long teacherId =
		 * this.tem.persistAndGetId(this.teacher, Long.class); Teacher found2 =
		 * this.tem.persistFlushFind(this.teacher);
		 */
	}

}
