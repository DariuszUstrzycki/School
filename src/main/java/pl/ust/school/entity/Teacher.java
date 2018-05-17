
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
@Table(name = "teachers")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude= "teacherSubjects")
public class Teacher extends Person {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "teacher", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<TeacherSubject> teacherSubjects = new HashSet<>();

	/////////////// helper ///////////////////

	public void addTeacherSubject(TeacherSubject teacherSubject) {
		teacherSubjects.add(teacherSubject);
	}

	public void removeTeacherSubject(TeacherSubject teacherSubject) {
		teacherSubject.setTeacher(null);
	}

	/////////////// getters and setters ///////////////////

	public Set<TeacherSubject> getTeacherSubjects() {
		if (this.teacherSubjects == null) {
			this.teacherSubjects = new HashSet<>();
		}
		return this.teacherSubjects;
	}
	
	/////////////// remove ///////////////////
	
	public void remove() {
		this.setDeleted(true);
		this.removeAllTeacherSubjects();
	}
	
	private void removeAllTeacherSubjects() {
		
		for(TeacherSubject ts : this.getTeacherSubjects()) {
			ts.setTeacher(null);
		}
		this.teacherSubjects.clear();
	}
}
