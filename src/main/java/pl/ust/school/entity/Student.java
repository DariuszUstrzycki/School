package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "schoolForm_id")
	private SchoolForm schoolForm;

	/////////////// getters and setters ///////////////////

	public SchoolForm getSchoolForm() {
		return schoolForm;
	}

	public void setSchoolForm(SchoolForm schoolForm) {

		if (schoolForm != null) {
			schoolForm.getStudents().add(this);
		} 
		
		if (this.schoolForm != null) { // student takes care of unlisting from the previous Form before new Form is set
			this.schoolForm.getStudents().remove(this);
		}

		this.schoolForm = schoolForm;
	}

	@Override
	public String toString() {
		return "Student [" + schoolForm + ", " + super.toString() + "]";
	}
	
	
	
}