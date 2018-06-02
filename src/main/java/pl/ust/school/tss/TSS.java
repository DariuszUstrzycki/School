package pl.ust.school.tss;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.model.BaseEntity;
import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.subject.Subject;
import pl.ust.school.teacher.Teacher;

/**
 * JavaBean domain object representing a triplet of a Teacher, a Subject and a Schoolform
 */
@Entity
@Table(name = "teachers_subjects_schoolforms")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class TSS extends BaseEntity {
	
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
	

	@Override
	public int hashCode() {
		return Objects.hash(this.subject, this.teacher, this.schoolform);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TSS that = (TSS) o;
		return Objects.equals(this.subject, that.subject) && 
				Objects.equals(this.teacher, that.teacher) &&
				Objects.equals(this.schoolform, that.schoolform);
	}

	public void removeTSS() {
		
		if(this.teacher != null) {
			this.teacher.getTsses().remove(this);
		}
		
		if(this.subject != null) {
			this.subject.getTsses().remove(this);
		}
		
		if(this.schoolform != null) {
			this.schoolform.getTsses().remove(this);
		}
		
	}

}
