package pl.ust.school.schoolform;

import java.util.Collection;
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
import pl.ust.school.model.NamedEntity;
import pl.ust.school.student.Student;
import pl.ust.school.student.StudentDto;
import pl.ust.school.tss.TSS;

@Entity
@Table(name = "schoolforms") 
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @ToString(callSuper=true, exclude= { "students", "tsses" })
public class Schoolform extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private Collection<Student> students; 
	
	/**
	 * @param tsses = objects of type TSS (TeacherSubjectSchoolform), eg Smith/Maths/FirstYear1A
	 */
	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Collection<TSS> tsses; 

	/////////////// getters and setters ///////////////////

	public Collection<Student> getStudents() {
		if (this.students == null) {
			this.students = new HashSet<>();
		}
		return this.students;
	}

	public Collection<TSS> getTsses() {
		if (this.tsses == null) {
			this.tsses = new HashSet<>();
		}
		return this.tsses;
	}

	/////////////// helpers for Students ///////////////////

	public void addStudent(Student s) {
		students.add(s);
		s.setSchoolform(this);
	}

	public void removeStudent(Student s) {
		students.remove(s);
		s.setSchoolform(null);
	}
	
	public void removeAllStudents() {
		
		for (Student s : this.getStudents()) {
			s.setSchoolform(null);
		}
		this.students.clear();
	}
	
	/////////////// helpers for TSS ///////////////////
	
	
	public void addTSS(TSS ts) {
		tsses.add(ts);
		ts.setSchoolform(this);
	}
	
	public void removeTSS(TSS ts) {
		ts.setSchoolform(null);
		tsses.remove(ts);
	}
	
	
	public void removeAllTSSs() {
		
		for (TSS ts : this.getTsses()) {
			ts.setSchoolform(null);
		}
		this.students.clear();
	}
	
	
}
