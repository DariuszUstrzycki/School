package pl.ust.school.test.entity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.context.transaction.BeforeTransaction;

import pl.ust.school.entity.SchoolForm;
import pl.ust.school.entity.Student;

public class StudentFormRelationTest {
	
	private Student getStudentWithId1AndFormId1() {
		
		Student student = new Student();
		student.setId(1L);
		SchoolForm form = new SchoolForm();
		form.setId(1L);
		student.setSchoolForm(form);
		
		return student;
	}
	
	@Test
	public void shouldSetFormAndIdOnStudent() {
		assertTrue(getStudentWithId1AndFormId1().getId() == 1);
		assertTrue(getStudentWithId1AndFormId1().getSchoolForm().getId() == 1);
	}
	
	@Test
	public void shouldAddStudentInFormObject_WhenSetFormIsCalledOnStudentObject() {
		long noOfStudentsInForm = 1;
		assertTrue(getStudentWithId1AndFormId1().getSchoolForm().getStudents().size() == noOfStudentsInForm);
	}

	@Test
	public void shouldRemoveStudentFromPreviousForm_WhenStudentIsUpdatedWithNewForm() {
		
		Student studentWithFormAndId = getStudentWithId1AndFormId1();
		SchoolForm initialForm = studentWithFormAndId.getSchoolForm();
		
		SchoolForm newForm = new SchoolForm();
		long newFormId = 2L;
		newForm.setId(newFormId);
		studentWithFormAndId.setSchoolForm(newForm);
		
		assertTrue(initialForm.getStudents().size() == 0);
		assertTrue(newForm.getStudents().size() == 1);
		assertTrue(studentWithFormAndId.getSchoolForm().getId() == newFormId);
	}
	
	@Test
	public void shouldRemoveStudentFromForm_WhenStudentIsRemoved() {
		
		Student student = new Student();
		SchoolForm form1 = new SchoolForm();
		form1.setId(1L);
		student.setSchoolForm(form1);
		
		assertTrue(student.getSchoolForm().getId() == 1);
		assertTrue(form1.getStudents().size() == 1);
		
		form1.removeStudent(student);
		
		assertTrue(form1.getStudents().size() == 0);
		assertNull(student.getSchoolForm());
	}

}
