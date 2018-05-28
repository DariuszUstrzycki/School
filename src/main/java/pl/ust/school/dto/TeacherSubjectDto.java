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
public class TeacherSubjectDto {
	
	private long id;
	private boolean isDeleted;
	private Teacher teacher;
	private Subject subject;
	private Schoolform schoolform;
	
	public boolean isNew() {
        return this.id < 1;
    }
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(boolean isDeleted) {
		 this.isDeleted = isDeleted;
	}

}
