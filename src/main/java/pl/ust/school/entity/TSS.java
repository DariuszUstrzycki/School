package pl.ust.school.entity;

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

@Entity
@Table(name = "teachers_subjects")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
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
		teacher.getTsses().remove(this);
		subject.getTsses().remove(this);
		schoolform.getTsses().remove(this);
	}

}
