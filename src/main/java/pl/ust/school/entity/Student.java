package pl.ust.school.entity;

import java.util.HashSet;
import java.util.Set;

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
	private SchoolForm schoolForm;

	/////////////// getters and setters ///////////////////


	public void setSchoolForm(SchoolForm newSchoolForm) {

		if (newSchoolForm != null) {
			newSchoolForm.getStudents().add(this);
		} 
		
		if (this.schoolForm != null && newSchoolForm != null) { // SchoolForm class takes care of removal when argument schoolForm is null
			this.schoolForm.getStudents().remove(this);
		}

		this.schoolForm = newSchoolForm;
	}
	
	///////////////// removal ///////////////////////////////
	
	public void remove() {
		this.getSchoolForm().getStudents().remove(this); // @WhereJoinTable ?!
		this.setDeleted(true);
	}
	
	
}