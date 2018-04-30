package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends StaffPerson {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "form_id")
	private SchoolForm form;

	/////////////// getters and setters ///////////////////

	public SchoolForm getForm() {
		return form;
	}

	public void setForm(SchoolForm form) {

		if (form != null) {
			form.getStudents().add(this);
		} 
		
		if (this.form != null) { // student takes care of unlisting from the previous Form before new Form is set
			this.form.getStudents().remove(this);
		}

		this.form = form;
	}
	
}