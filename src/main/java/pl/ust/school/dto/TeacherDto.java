package pl.ust.school.dto;

import java.util.Set;

import lombok.Getter;
import pl.ust.school.entity.TeacherSubject;

@Getter
public class TeacherDto {

	private Long id;
	private String name;
	private Set<TeacherSubject> teacherSubject;

	public static class Builder {

		private TeacherDto teacherDto = new TeacherDto();

		public Builder withId(Long id) {
			teacherDto.id = id;
			return this;
		}

		public Builder withName(String name) {
			teacherDto.name = name;
			return this;
		}
		
		public Builder withStudents(Set<TeacherSubject> teacherSubject) {
			teacherDto.teacherSubject = teacherSubject;
			return this;
		}

		public TeacherDto build() {
			return teacherDto;
		}

	}

}
