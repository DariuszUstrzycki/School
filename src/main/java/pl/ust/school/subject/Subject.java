package pl.ust.school.subject;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.school.model.NamedEntity;
import pl.ust.school.tss.TSS;

@Entity
@Table(name = "subjects")
@Where(clause = "is_deleted=false")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = false, exclude = "tsses")
public class Subject extends NamedEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * @param tsses = objects of type TSS (TeacherSubjectSchoolform), eg Smith/Maths/FirstYear1A
	 */
	@OneToMany(mappedBy = "subject", fetch = FetchType.EAGER) 
	private Set<TSS> tsses;

	/////////////// helper ///////////////////

	public void addTSS(TSS tSS) {
		tsses.add(tSS);
	}

	public void removeTSS(TSS tSS) {
		tSS.setSubject(null);
	}

	/////////////// getters and setters ///////////////////

	public Set<TSS> getTsses() {
		if (this.tsses == null) {
			this.tsses = new TreeSet<>();
		}
		return this.tsses;
	}

}
