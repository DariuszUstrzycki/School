package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.TSS;

@Component
public class TSSMapper {
	
	public TSSDto toDTO(TSS tSS){
		return TSSDto.builder()
					.id(tSS.getId())
					.isDeleted(tSS.isDeleted())
					.schoolform(tSS.getSchoolform())
					.subject(tSS.getSubject())
					.teacher(tSS.getTeacher())
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
		TSS tSS = new TSS();
		tSS.setId(dto.getId());
		tSS.setDeleted(dto.getIsDeleted());
		tSS.setSchoolform(dto.getSchoolform());;
		tSS.setSchoolform(dto.getSchoolform());
		tSS.setTeacher(dto.getTeacher());
		return tSS;
	}
	
	

}
