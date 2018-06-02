package pl.ust.school.student;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.grade.Grade;
import pl.ust.school.model.Person;
import pl.ust.school.schoolform.Schoolform;

@Entity
@Table(name = "students")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false)
public class Student extends Person {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "schoolform_id")
	private Schoolform schoolform;
	
	@OneToMany(mappedBy = "student")
	private List <Grade> grades = new ArrayList<>();
	
	
}