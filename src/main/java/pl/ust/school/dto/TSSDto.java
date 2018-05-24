package pl.ust.school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.entity.Schoolform;
import pl.ust.school.entity.Subject;
import pl.ust.school.entity.Teacher;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TSSDto {
	
	private long id;
	private boolean isDeleted;

	private Schoolform schoolform;
	private Subject subject;
	private Teacher teacher;
	
	public boolean isNew() {
        return this.id < 1;
    }
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(boolean isDeleted) {
		 this.isDeleted = isDeleted;
	}
	
	@Override
	public String toString() {
		String unassigned = "unassigned";
		return "TS:" + this.getId() + ". " 
				+ ((subject == null) ? unassigned : subject.getName())+ "-" 
				+ ((teacher == null) ? "" : teacher.getFirstName()) + " " 
				+ ((teacher == null) ? unassigned : teacher.getLastName())	+ "-" 
				+ ((schoolform == null) ? unassigned : schoolform.getName()) + "<br>";
	}
}

