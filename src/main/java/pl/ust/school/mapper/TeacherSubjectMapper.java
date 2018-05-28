package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.TeacherSubjectDto;
import pl.ust.school.entity.TeacherSubject;

@Component
public class TeacherSubjectMapper {
	
	public TeacherSubjectDto toDTO(TeacherSubject teacherSubject){
		return TeacherSubjectDto.builder()
					.id(teacherSubject.getId())
					.isDeleted(teacherSubject.isDeleted())
					.schoolform(teacherSubject.getSchoolform())
					.subject(teacherSubject.getSubject())
					.teacher(teacherSubject.getTeacher())
					.build();
	}
	
	public Optional<TeacherSubjectDto> toDTO(Optional<TeacherSubject> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}
	
	public TeacherSubject fromDTO(TeacherSubjectDto dto) {
		TeacherSubject teacherSubject = new TeacherSubject();
		teacherSubject.setId(dto.getId());
		teacherSubject.setDeleted(dto.getIsDeleted());
		teacherSubject.setSchoolform(dto.getSchoolform());;
		teacherSubject.setSchoolform(dto.getSchoolform());
		teacherSubject.setTeacher(dto.getTeacher());
		return teacherSubject;
	}
	
	

}
