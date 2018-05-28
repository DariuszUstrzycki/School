package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.SchoolformDto;
import pl.ust.school.entity.Schoolform;

@Component
public class SchoolformMapper {
	
	public SchoolformDto toDTO(Schoolform schoolform){
		return SchoolformDto.builder()
					.id(schoolform.getId())
					.isDeleted(schoolform.isDeleted())
					.name(schoolform.getName())
					.students(schoolform.getStudents())
					.build();
	}
	
	public Optional<SchoolformDto> toDTO(Optional<Schoolform> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}

	public Schoolform fromDTO(SchoolformDto dto) {
		
		Schoolform schoolform = new Schoolform();
		schoolform.setId(dto.getId());
		schoolform.setDeleted(dto.getIsDeleted());
		schoolform.setName(dto.getName());
		schoolform.setStudents(dto.getStudents());
		return schoolform;
	}
	
}
