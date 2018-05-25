package pl.ust.school.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "teachers_subjects")
@Where(clause = "is_deleted=false")
@Getter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class TeacherSubject extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	
	
	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	public TeacherSubject(Teacher teacher, Subject subject) {
		super();
		this.teacher = teacher;
		this.subject = subject;
	}
	
	
	/////////////// getters and setters ///////////////////

	public void setTeacher(Teacher newTeacher) {

		if (newTeacher != null) {
			newTeacher.getSubjects().add(this);
		}

		if (this.teacher != null && newTeacher != null) { // Teacher class takes care of removal when argument subject is null
			this.teacher.getSubjects().remove(this);
		}

		this.teacher = newTeacher;
	}

	public void setSubject(Subject newSubject) {
		
		if (newSubject != null) {
			newSubject.getTeachers().add(this);
		}

		if (this.subject != null && newSubject != null) { // Subject class takes care of removal when argument subject is null
			this.subject.getTeachers().remove(this);
		}

		this.subject = newSubject;
	}

	///////////////// removal ///////////////////////////////

	public void remove() {
		this.getTeacher().getSubjects().remove(this); // @WhereJoinTable ?!
		this.getSubject().getTeachers().remove(this);
		this.setDeleted(true);
	}

	@Override
	public int hashCode() {
		return Objects.hash( this.subject, this.teacher );
	}

	@Override
	public boolean equals(Object o) {
		 if ( this == o ) {
	            return true;
	        }
	        if ( o == null || getClass() != o.getClass() ) {
	            return false;
	        }
	        TeacherSubject that = (TeacherSubject) o;
	        return Objects.equals( this.subject, that.subject ) &&
	                Objects.equals( this.teacher, that.teacher );
	}


}
