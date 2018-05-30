package pl.ust.school.schoolform;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class SchoolformMapper {
	
	public SchoolformDto toDTO(Schoolform schoolform){
		return SchoolformDto.builder()
					.id(schoolform.getId())
					.isDeleted(schoolform.isDeleted())
					.name(schoolform.getName())
					.students(schoolform.getStudents())
					.tsses(schoolform.getTsses())
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
		schoolform.setTsses(dto.getTsses());
		return schoolform;
	}
	
}
