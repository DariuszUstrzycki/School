package pl.ust.school.mapper;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.SubjectDto;
import pl.ust.school.entity.Subject;

@Component
public class SubjectMapper {
	
	public SubjectDto toDTO(Subject subject){
		return SubjectDto.builder()
					.id(subject.getId())
					.isDeleted(subject.isDeleted())
					.name(subject.getName())
					.teacherSubjects(subject.getTeacherSubjects())
					.build();
	}

	public Subject fromDTO(SubjectDto dto) {
		Subject subject = new Subject();
		subject.setId(dto.getId());
		subject.setDeleted(dto.getIsDeleted());
		subject.setName(dto.getName());
		subject.setTeacherSubjects(dto.getTeacherSubjects());
		return subject;
	}

}