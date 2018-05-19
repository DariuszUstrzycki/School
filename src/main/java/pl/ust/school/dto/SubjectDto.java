package pl.ust.school.dto;

import java.util.Set;

import lombok.Getter;
import pl.ust.school.entity.TeacherSubject;

@Getter
public class SubjectDto {

	private Long id;
	private String name;
	private Set<TeacherSubject> teacherSubjects;

	public static class Builder {

		private SubjectDto subjectDto = new SubjectDto();

		public Builder withId(Long id) {
			subjectDto.id = id;
			return this;
		}

		public Builder withName(String name) {
			subjectDto.name = name;
			return this;
		}
		
		public Builder withStudents(Set<TeacherSubject> teacherSubjects) {
			subjectDto.teacherSubjects = teacherSubjects;
			return this;
		}

		public SubjectDto build() {
			return subjectDto;
		}

	}

}
