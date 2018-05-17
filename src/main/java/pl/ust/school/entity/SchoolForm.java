package pl.ust.school.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "schoolForms")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @ToString(callSuper=true, exclude= "students")
public class SchoolForm extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "schoolForm", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<Student> students; 

	/////////////// helper ///////////////////

	public void addStudent(Student student) {
		students.add(student);
	}

	public void removeStudent(Student student) {
		student.setSchoolForm(null);
	}

	/////////////// getters and setters ///////////////////

	public Set<Student> getStudents() {
		if (this.students == null) {
			this.students = new HashSet<>();
		}
		return this.students;
	}

	/////////////// remove ///////////////////

	public void remove() {
		this.setDeleted(true);
		this.removeAllStudents();
	}

	private void removeAllStudents() {
		
		for (Student s : this.getStudents()) {
			s.setSchoolForm(null);
		}
		this.students.clear();
	}
	
}
