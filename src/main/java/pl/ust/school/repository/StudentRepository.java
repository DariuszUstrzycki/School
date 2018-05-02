package pl.ust.school.repository;

import java.util.Collection;

import pl.ust.school.entity.Student;

public interface StudentRepository extends AppBaseRepository<Student, Long> {
	
	Collection<Student> findBySchoolForm_Id(long id);

}
