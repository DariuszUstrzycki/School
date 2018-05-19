package pl.ust.school.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import pl.ust.school.entity.Student;

@Builder @Getter
public class SchoolFormDto {

	private Long id;
	private Boolean isDeleted;
	private String name;
	private Set<Student> students; 
}
