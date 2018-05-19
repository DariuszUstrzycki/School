package pl.ust.school.mapper;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.SchoolFormDto;
import pl.ust.school.entity.SchoolForm;

@Component
public class SchoolFormMapper {
	
	public SchoolFormDto toDTO(SchoolForm schoolForm){
		return SchoolFormDto.builder()
					.id(schoolForm.getId())
					.isDeleted(schoolForm.isDeleted())
					.name(schoolForm.getName())
					.students(schoolForm.getStudents())
					.build();
	}

	public SchoolForm fromDTO(SchoolFormDto dto) {
		SchoolForm schoolForm = new SchoolForm();
		schoolForm.setId(dto.getId());
		schoolForm.setDeleted(dto.getIsDeleted());
		schoolForm.setName(dto.getName());
		schoolForm.setStudents(dto.getStudents());
		return schoolForm;
	}
	
}
