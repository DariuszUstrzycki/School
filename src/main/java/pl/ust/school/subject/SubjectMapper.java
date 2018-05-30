package pl.ust.school.subject;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
	
	public SubjectDto toDTO(Subject subject){
		return SubjectDto.builder()
					.id(subject.getId())
					.isDeleted(subject.isDeleted())
					.name(subject.getName())
					.tsses(subject.getTsses())
					.build();
	}
	
	public Optional<SubjectDto> toDTO(Optional<Subject> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}

	public Subject fromDTO(SubjectDto subjectDto) {
		
		Subject subject = new Subject();
		subject.setId(subjectDto.getId());
		subject.setDeleted(subjectDto.getIsDeleted());
		subject.setName(subjectDto.getName());
		subject.setTsses(subjectDto.getTsses());
		return subject;
	}

	

}