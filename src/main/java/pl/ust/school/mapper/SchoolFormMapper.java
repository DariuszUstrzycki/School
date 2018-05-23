package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.SchoolFormDto;
import pl.ust.school.dto.SubjectDto;
import pl.ust.school.entity.SchoolForm;
import pl.ust.school.entity.Subject;

@Component
public class SchoolFormMapper {
	
	public SchoolFormDto toDTO(SchoolForm schoolForm){
		return SchoolFormDto.builder()
					.id(schoolForm.getId())
					.isDeleted(schoolForm.isDeleted())
					.name(schoolForm.getName())
					.students(schoolForm.getStudents())
					.teacherSubjectSchforms(schoolForm.getTeacherSubjectSchforms())
					.build();
	}
	
	public Optional<SchoolFormDto> toDTO(Optional<SchoolForm> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}

	public SchoolForm fromDTO(SchoolFormDto schoolFormDto) {
		
		SchoolForm schoolForm = new SchoolForm();
		schoolForm.setId(schoolFormDto.getId());
		
		schoolForm.setDeleted(schoolFormDto.getIsDeleted());
		schoolForm.setName(schoolFormDto.getName());
		schoolForm.setStudents(schoolFormDto.getStudents());
		schoolForm.setTeacherSubjectSchforms(schoolFormDto.getTeacherSubjectSchforms());
		return schoolForm;
	}
	
}
