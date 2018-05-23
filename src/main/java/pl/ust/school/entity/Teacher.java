
package pl.ust.school.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "teachers")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude= "teacherSubjectSchforms")
public class Teacher extends Person {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "teacher", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch=FetchType.EAGER)
	private Set<TeacherSubjectSchform> teacherSubjectSchforms;

	/////////////// helper ///////////////////

	public void addTeacherSubjectSchform(TeacherSubjectSchform tss) {
		teacherSubjectSchforms.add(tss);
	}

	
	// UWAGA: schoolform i subject tego nie mają:
	public void removeTeacherSubjectSchform(long tssId) {
		for(TeacherSubjectSchform ts : this.getTeacherSubjectSchforms()) {
			if(ts.getId() == tssId) {
				//ts.setTeacher(null);
				ts.setDeleted(true);
			}
		}
	}

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
			//ts.setTeacher(null);
			ts.setDeleted(true);
		}
		this.teacherSubjectSchforms.clear();
	}
}
