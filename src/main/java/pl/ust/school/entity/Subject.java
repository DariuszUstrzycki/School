package pl.ust.school.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "subjects")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude= "teacherSubjectSchforms")
public class Subject extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "subject", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<TeacherSubjectSchform> teacherSubjectSchforms;

	/////////////// helper ///////////////////

	public void addTeacherSubjectSchform(TeacherSubjectSchform tss) {
		teacherSubjectSchforms.add(tss);
	}
/*
	public void removeTeacherSubjectSchform(TeacherSubjectSchform tss) {

		tss.setSubject(null); 
		
		// UWAGA: Teacher ma zupełnie inaczej!!!!
	}
*/
	/////////////// getters and setters ///////////////////

	public Set<TeacherSubjectSchform> getTeacherSubjectSchforms() {
		if (this.teacherSubjectSchforms == null) {
			this.teacherSubjectSchforms = new HashSet<>();
		}
		return this.teacherSubjectSchforms;
	}
	
	/////////////// remove ///////////////////
	
	public void remove() {
		this.setDeleted(true);
		this.removeAllTeacherSubjectSchforms();
	}
	
	private void removeAllTeacherSubjectSchforms() {
		
		for(TeacherSubjectSchform ts : this.getTeacherSubjectSchforms()) {
			//ts.setSubject(null);
			ts.setDeleted(true);
		}
		this.teacherSubjectSchforms.clear();
	}

}
