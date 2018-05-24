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
@Table(name = "schoolforms") 
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @ToString(callSuper=true, exclude= { "students", "tSSs" })
public class Schoolform extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<Student> students; 
	
	@OneToMany(mappedBy = "schoolform", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<TSS> tSSs; 

	/////////////// helper ///////////////////

	public void addStudent(Student s) {
		students.add(s);
	}

	public void removeStudent(Student s) {
		s.setSchoolform(null);
	}
	
	public void addTSS(TSS ts) {
		tSSs.add(ts);
	}

	public void removeTSS(TSS tss) {
		tss.setSchoolform(null);
	}

	/////////////// getters and setters ///////////////////

	public Set<Student> getStudents() {
		if (this.students == null) {
			this.students = new HashSet<>();
		}
		return this.students;
	}
	
	public Set<TSS> getTSSs() {
		if (this.tSSs == null) {
			this.tSSs = new HashSet<>();
		}
		return this.tSSs;
	}

	/////////////// remove ///////////////////

	public void remove() {
		this.setDeleted(true);
		this.removeAllStudents();
		this.removeAllTSSs();
	}

	private void removeAllStudents() {
		
		for (Student s : this.getStudents()) {
			s.setSchoolform(null);
		}
		this.students.clear();
	}
	
	private void removeAllTSSs() {
		
		for (TSS s : this.getTSSs()) {
			s.setSchoolform(null);
		}
		this.tSSs.clear();
	}
	
}
