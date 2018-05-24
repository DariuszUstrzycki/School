
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
@ToString(callSuper=true, includeFieldNames = false, exclude= "tSSs")
public class Teacher extends Person {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "teacher", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch=FetchType.EAGER)
	private Set<TSS> tSSs;

	/////////////// helper ///////////////////

	public void addTSS(TSS tss) {
		tSSs.add(tss);
	}

	public void removeTSS(long tssId) {
		for(TSS ts : this.getTSSs()) {
			if(ts.getId() == tssId) {
				//ts.setTeacher(null);
				ts.setDeleted(true);
			}
		}
	}

	/////////////// getters and setters ///////////////////

	public Set<TSS> getTSSs() {
		if (this.tSSs == null) {
			this.tSSs = new HashSet<>();
		}
		return this.tSSs;
	}
	
	/////////////// remove ///////////////////
	
	public void remove() {
		this.setDeleted(true);
		this.removeAllTSSs();
	}
	
	private void removeAllTSSs() {
		
		for(TSS ts : this.getTSSs()) {
			//ts.setTeacher(null);
			ts.setDeleted(true);
		}
		this.tSSs.clear();
	}
}
