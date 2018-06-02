package pl.ust.school.grade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.model.BaseEntity;
import pl.ust.school.student.Student;
import pl.ust.school.subject.Subject;

@Entity
@Table(name = "grades")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(includeFieldNames = false, callSuper=true, exclude= { "student"})
public class Grade extends BaseEntity{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotEmpty
	@Column(name = "grade_value", nullable = false)
	private String gradeValue;
	
	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
		

}
