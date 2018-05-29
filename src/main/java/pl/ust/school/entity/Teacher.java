
package pl.ust.school.entity;

import java.util.Set;
import java.util.TreeSet;

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
@ToString(callSuper=true, includeFieldNames = false, exclude= "tsses")
public class Teacher extends Person {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<TSS> tsses;

	/////////////// helper ///////////////////

	public void addTSS(Subject subject) {
		TSS tSS = new TSS(this, subject, null);
		this.tsses.add(tSS);
		subject.getTsses().add(tSS);
	}
	
	public void removeTSS(Subject subject) {
		TSS tSS = new TSS(this, subject, null);
		//tSS.getSchoolform().getTSSs().remove(tSS); // new line
		subject.getTsses().remove(tSS);
		this.tsses.remove(tSS);
		tSS.setTeacher(null);
		tSS.setSubject(null);
		//tSS.setSchoolform(null);
	}
	
	public void removeTeacherFromAllTSSs() {
		
		for(TSS tSS : this.getTsses()) {
			tSS.setTeacher(null);
		}
	}

	/////////////// getters and setters ///////////////////

	public Set<TSS> getTsses() {
		if (this.tsses == null) {
			this.tsses = new TreeSet<>();
		}
		return this.tsses;
	}
	
}
