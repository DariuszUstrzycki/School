package pl.ust.school.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "teacher", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<TeacherSubject> teacherSubjects = new HashSet<>();

	/////////////// helper ///////////////////

	public void addTeacherSubject(TeacherSubject teacherSubject) {
		teacherSubjects.add(teacherSubject);
	}

	public void removeTeacherSubject(TeacherSubject teacherSubject) {
		teacherSubject.setSubject(null); // Subject methods are identical to Teacher's, with this exception
											// setTeacher(null)
	}

	/////////////// getters and setters ///////////////////

	protected Set<TeacherSubject> getTeacherSubjectsInternal() {
		if (this.teacherSubjects == null) {
			this.teacherSubjects = new HashSet<>();
		}
		return this.teacherSubjects;
	}

	protected void setTeacherSubjectsInternal(Set<TeacherSubject> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}

	public Set<TeacherSubject> getTeacherSubjects() {
		return getTeacherSubjectsInternal();
	}

	public void setTeacherSubjects(Set<TeacherSubject> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}

}
