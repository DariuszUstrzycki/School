package pl.ust.school.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "schoolForms")
public class SchoolForm extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "schoolForm", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<Student> students; // = new HashSet<>();

	/////////////// helper ///////////////////

	public void addStudent(Student student) {
		students.add(student);
	}

	public void removeStudent(Student student) {
		student.setSchoolForm(null);
	}

	/////////////// getters and setters ///////////////////

	protected Set<Student> getStudentsInternal() {
		if (this.students == null) {
			this.students = new HashSet<>();
		}
		return this.students;
	}

	protected void setStudentsInternal(Set<Student> students) {
		this.students = students;
	}

	public Set<Student> getStudents() {
		return getStudentsInternal();
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
