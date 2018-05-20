package pl.ust.school.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.entity.Student;

@Builder 
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SchoolFormDto {

	private Long id;
	private Boolean isDeleted;
	@NotEmpty
	private String name;
	private Set<Student> students; 
}
