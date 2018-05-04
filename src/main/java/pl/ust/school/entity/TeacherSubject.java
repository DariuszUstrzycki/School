package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teachers_subjects")
public class TeacherSubject extends IdEntity {
	
	@ManyToOne@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@ManyToOne@JoinColumn(name = "teacher_id")
	private Teacher teacher;	
	
	//////////////////////////////////////////////////////
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {

		if (teacher != null) {
			teacher.getTeacherSubjects().add(this);
		} 
		
		if (this.teacher != null) { // TeacherSubject takes care of unlisting from the previous teacher before new teacher is set
			this.teacher.getTeacherSubjects().remove(this);
		}

		this.teacher = teacher;
	}
	
	/////////////// getters and setters ///////////////////
	
	public Subject getSubject() {
		return subject;
	}
	
	public void setSubject(Subject subject) {
		if (subject != null) {
			subject.getTeacherSubjects().add(this);
		} 
		
		if (this.subject != null) { // TeacherSubject takes care of unlisting from the previous teacher before new teacher is set
			this.subject.getTeacherSubjects().remove(this);
		}

		this.subject = subject;
	}
	@Override
	public String toString() {
		return "TeachSub: " + subject + ", " + teacher;
	}
	
	

}
