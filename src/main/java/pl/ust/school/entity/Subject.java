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
@ToString(callSuper=true, includeFieldNames = false, exclude= "tSSs")
public class Subject extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "subject", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Set<TSS> tSSs;

	/////////////// helper ///////////////////

	public void addTSS(TSS tss) {
		tSSs.add(tss);
	}

	public void removeTSS(TSS tss) {

		tss.setSubject(null); // Subject methods are identical to Teacher's, with this exception
											// setTeacher(null)
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
			ts.setSubject(null);
		}
		this.tSSs.clear();
	}

}
