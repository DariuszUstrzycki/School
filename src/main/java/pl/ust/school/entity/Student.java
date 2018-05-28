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
	@JoinColumn(name = "schoolform_id")
	private Schoolform schoolform;

	/////////////// getters and setters ///////////////////


	public void setSchoolform(Schoolform newSchoolform) {

		/*if (newSchoolform != null) {
			newSchoolform.getStudents().add(this);
		} 
		
		if (this.schoolform != null && newSchoolform != null) { // Schoolform class takes care of removal when argument schoolform is null
			this.schoolform.getStudents().remove(this);
		}*/

		this.schoolform = newSchoolform;
	}
	
	///////////////// removal ///////////////////////////////
	
	public void remove() {
		//this.getSchoolform().getStudents().remove(this); // @WhereJoinTable ?!
		this.setDeleted(true);
	}
	
	
}