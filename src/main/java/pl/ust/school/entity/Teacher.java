
package pl.ust.school.entity;

import java.util.Set;
import java.util.TreeSet;

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
@Table(name = "teachers")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude= "subjects")
public class Teacher extends Person {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<TeacherSubject> subjects;

	/////////////// helper ///////////////////

	public void addSubject(Subject subject) {
		TeacherSubject teacherSubject = new TeacherSubject(this, subject);
		this.subjects.add(teacherSubject);
		subject.getTeachers().add(teacherSubject);
	}
	
	// person1.removeSubject( subject ); >> DELETE  FROM TeacherSubject
	//                               WHERE   teacher_id = 1 AND subject_id = 3
	
	public void removeSubject(Subject subject) {
		TeacherSubject teacherSubject = new TeacherSubject(this, subject);
		subject.getTeachers().remove(teacherSubject);
		this.subjects.remove(teacherSubject);
		teacherSubject.setTeacher(null);
		teacherSubject.setSubject(null);
		//teacherSubject.setDeleted(true);
	}

	/////////////// getters and setters ///////////////////

	public Set<TeacherSubject> getSubjects() {
		if (this.subjects == null) {
			this.subjects = new TreeSet<>();
		}
		return this.subjects;
	}
	
	/////////////// remove ///////////////////
	
	public void remove() {
		this.setDeleted(true);
		//this.removeAllSubjects();
	}
	/*
	private void removeAllSubjects() {
		
		for(TeacherSubject teacherSubject : this.getSubjects()) {
			//teacherSubjectId.setTeacher(null);
			teacherSubject.setDeleted(true);
		}
		this.subjects.clear();
		
	}*/
	
	///////////////////////////
	
	
}
