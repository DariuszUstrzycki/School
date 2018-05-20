package pl.ust.school.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.entity.TeacherSubject;

@Builder 
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubjectDto {

	
	private Long id;
	private Boolean isDeleted;
	@NotEmpty
	private String name;
	private Set<TeacherSubject> teacherSubjects;
	
	
}
