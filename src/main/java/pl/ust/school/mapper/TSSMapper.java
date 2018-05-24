package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.TSS;

@Component
public class TSSMapper {
	
	public TSSDto toDTO(TSS ts){
		return TSSDto.builder()
					.id(ts.getId())
					.isDeleted(ts.isDeleted())
					.schoolform(ts.getSchoolform()) 
					.subject(ts.getSubject())
					.teacher(ts.getTeacher())
					.build();
	}
	
	public Optional<TSSDto> toDTO(Optional<TSS> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}
	
	public TSS fromDTO(TSSDto dto) {
		TSS student = new TSS();
		student.setId(dto.getId());
		student.setDeleted(dto.getIsDeleted());
		student.setSchoolform(dto.getSchoolform());
		student.setSubject(dto.getSubject());
		student.setTeacher(dto.getTeacher());
		return student;
	}
	
	

}
