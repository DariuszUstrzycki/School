package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "teachers_subjects")
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

	public void setTeacher(Teacher teacher) {

		if (teacher != null) {
			teacher.getTeacherSubjects().add(this);
		}

		if (this.teacher != null) { // TeacherSubject takes care of unlisting from the previous teacher before new
									// teacher is set
			this.teacher.getTeacherSubjects().remove(this);
		}

		this.teacher = teacher;
	}

	public void setSubject(Subject subject) {
		if (subject != null) {
			subject.getTeacherSubjects().add(this);
		}

		if (this.subject != null) { // TeacherSubject takes care of unlisting from the previous teacher before new
									// teacher is set
			this.subject.getTeacherSubjects().remove(this);
		}

		this.subject = subject;
	}

}
