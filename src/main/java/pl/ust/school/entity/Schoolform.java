package pl.ust.school.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "schoolforms") 
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @ToString(callSuper=true, exclude= { "students", "teacherSubjects" })
public class Schoolform extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private Set<Student> students; 
	
	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private Set<TeacherSubject> teacherSubjects; 
	
	/////////////// helper ///////////////////

	public void addStudent(Student s) {
		students.add(s);
		s.setSchoolform(this);
	}

	public void removeStudent(Student s) {
		students.remove(s);
		s.setSchoolform(null);
	}
	
	
	public void addTeacherSubject(TeacherSubject ts) {
		teacherSubjects.add(ts);
		ts.setSchoolform(this);
	}
	
	public void removeTeacherSubject(TeacherSubject ts) {
		teacherSubjects.remove(ts);
		ts.setSchoolform(null);
	}
	
	/////////////// getters and setters ///////////////////

	public Set<Student> getStudents() {
		if (this.students == null) {
			this.students = new HashSet<>();
		}
		return this.students;
	}
	
	public void removeAllStudents() {
		
		for (Student s : this.getStudents()) {
			s.setSchoolform(null);
		}
		this.students.clear();
	}
	
	public Set<TeacherSubject> getTeacherSubjects() {
		if (this.teacherSubjects == null) {
			this.teacherSubjects = new HashSet<>();
		}
		return this.teacherSubjects;
	}
	
	public void removeAllTeacherSubjects() {
		
		for (TeacherSubject ts : this.getTeacherSubjects()) {
			ts.setSchoolform(null);
		}
		this.students.clear();
	}
	
}
