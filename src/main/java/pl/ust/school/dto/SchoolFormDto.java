package pl.ust.school.dto;

import java.util.Set;

import lombok.Getter;
import pl.ust.school.entity.Student;

@Getter
public class SchoolFormDto {

	private Long id;
	private String name;
	private Set<Student> students; 

	public static class Builder {

		private SchoolFormDto schoolFormDto = new SchoolFormDto();

		public Builder withId(Long id) {
			schoolFormDto.id = id;
			return this;
		}

		public Builder withName(String name) {
			schoolFormDto.name = name;
			return this;
		}
		
		public Builder withStudents(Set<Student> students) {
			schoolFormDto.students = students;
			return this;
		}

		public SchoolFormDto build() {
			return schoolFormDto;
		}

	}

}
