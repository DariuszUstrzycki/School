package pl.ust.school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.entity.SchoolForm;
import pl.ust.school.entity.Subject;
import pl.ust.school.entity.Teacher;

@Builder 
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeacherSubjectSchformDto {
	
	private long id;
	private boolean isDeleted;
	
	private Subject subject;
	private Teacher teacher;
	private SchoolForm schoolForm;
	
	public boolean isNew() {
        return this.id < 1;
    }
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public boolean setIsDeleted(boolean isDeleted) {
		return this.isDeleted = isDeleted;
	}

}
