package pl.ust.school.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import pl.ust.school.entity.TeacherSubject;

@Builder @Getter
public class SubjectDto {

	private Long id;
	private Boolean isDeleted;
	private String name;
	private Set<TeacherSubject> teacherSubjects;
	
}
