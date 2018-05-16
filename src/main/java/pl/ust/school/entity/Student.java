package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "students")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false)
public class Student extends Person {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "schoolForm_id")
	@Getter private SchoolForm schoolForm;

	/////////////// getters and setters ///////////////////


	public void setSchoolForm(SchoolForm schoolForm) {

		if (schoolForm != null) {
			schoolForm.getStudents().add(this);
		} 
		
		if (this.schoolForm != null) { // student takes care of unlisting from the previous Form before new Form is set
			this.schoolForm.getStudents().remove(this);
		}

		this.schoolForm = schoolForm;
	}
	
}