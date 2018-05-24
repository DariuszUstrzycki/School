package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teachers_subjects_schoolforms")
@Where(clause = "is_deleted=false")
@Getter @NoArgsConstructor //@ToString(callSuper = true)
public class TSS extends IdEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name = "schoolform_id")
	private Schoolform schoolform;

	/////////////// getters and setters ///////////////////

	public void setTeacher(Teacher newTeacher) {

		if (newTeacher != null) {
			newTeacher.getTSSs().add(this);
		}

		if (this.teacher != null && newTeacher != null) { // Teacher class takes care of removal when argument subject is null
			this.teacher.getTSSs().remove(this);
		}

		this.teacher = newTeacher;
	}

	public void setSubject(Subject newSubject) {
		
		if (newSubject != null) {
			newSubject.getTSSs().add(this);
		}

		if (this.subject != null && newSubject != null) { // Subject class takes care of removal when argument subject is null
			this.subject.getTSSs().remove(this);
		}

		this.subject = newSubject;
	}
	
	public void setSchoolform(Schoolform newSchoolform) {
		
		if (newSchoolform != null) {
			newSchoolform.getTSSs().add(this);
		}

		if (this.schoolform != null && newSchoolform != null) { 
			this.schoolform.getTSSs().remove(this);
		}

		this.schoolform = newSchoolform;
	}

	///////////////// removal ///////////////////////////////

	public void remove() {
		this.getTeacher().getTSSs().remove(this); // @WhereJoinTable ?!
		this.getSubject().getTSSs().remove(this); 
		this.getSchoolform().getTSSs().remove(this);
		this.setDeleted(true);
	}
	
	
	///////////////////// equals / hashcode ////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schoolform == null) ? 0 : schoolform.hashCode());
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
		TSS other = (TSS) obj;
		if (schoolform == null) {
			if (other.schoolform != null)
				return false;
		} else if (!schoolform.equals(other.schoolform))
			return false;
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

	@Override
	public String toString() {
		return "TS:" + this.getId() + ". " 
				+ ((subject == null) ? "unassigned" : subject.getName())+ "-" 
				+ ((teacher == null) ? "" : teacher.getFirstName()) + " " 
				+ ((teacher == null) ? "unassigned" : teacher.getLastName())	+ "-" 
				+ ((schoolform == null) ? "unassigned" : schoolform.getName()) + "<br>";
	}
	
	///////////////// to string ///////////
	
	
	

}
