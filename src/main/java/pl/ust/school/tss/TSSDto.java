package pl.ust.school.tss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.schoolform.Schoolform;
import pl.ust.school.subject.Subject;
import pl.ust.school.teacher.Teacher;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TSSDto {
	
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
