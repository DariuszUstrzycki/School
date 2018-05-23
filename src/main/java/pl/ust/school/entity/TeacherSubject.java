package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "teachers_subjects")
@Where(clause = "is_deleted=false")
@Getter @NoArgsConstructor @ToString(callSuper = true)
public class TeacherSubject extends IdEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	/////////////// getters and setters ///////////////////

	public void setTeacher(Teacher newTeacher) {

		if (newTeacher != null) {
			newTeacher.getTeacherSubjects().add(this);
		}

		if (this.teacher != null && newTeacher != null) { // Teacher class takes care of removal when argument subject is null
			this.teacher.getTeacherSubjects().remove(this);
		}

		this.teacher = newTeacher;
	}

	public void setSubject(Subject newSubject) {
		
		if (newSubject != null) {
			newSubject.getTeacherSubjects().add(this);
		}

		if (this.subject != null && newSubject != null) { // Subject class takes care of removal when argument subject is null
			this.subject.getTeacherSubjects().remove(this);
		}

		this.subject = newSubject;
	}

	///////////////// removal ///////////////////////////////

	public void remove() {
		this.getTeacher().getTeacherSubjects().remove(this); // @WhereJoinTable ?!
		this.getSubject().getTeacherSubjects().remove(this); 
		this.setDeleted(true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeacherSubject other = (TeacherSubject) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		return true;
	}
	
	///////////////////// equals / hashcode ////////////////////
	
	

}
