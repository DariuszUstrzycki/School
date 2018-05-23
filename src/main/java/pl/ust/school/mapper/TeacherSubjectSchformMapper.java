package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.TeacherSubjectSchformDto;
import pl.ust.school.entity.TeacherSubjectSchform;

@Component
public class TeacherSubjectSchformMapper {

	public TeacherSubjectSchformDto toDTO(TeacherSubjectSchform tss) {
		return TeacherSubjectSchformDto.builder().id(tss.getId()).isDeleted(tss.isDeleted())
				.schoolForm(tss.getSchoolForm()).subject(tss.getSubject()).teacher(tss.getTeacher()).build();
	}
	
	public Optional<TeacherSubjectSchformDto> toDTO(Optional<TeacherSubjectSchform> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}

	public TeacherSubjectSchform fromDTO(TeacherSubjectSchformDto tssDto) {

		TeacherSubjectSchform tss = new TeacherSubjectSchform();
		tss.setId(tssDto.getId());
		tss.setDeleted(tssDto.getIsDeleted());
		tss.setSchoolForm(tssDto.getSchoolForm());
		tss.setSubject(tssDto.getSubject());
		tss.setTeacher(tssDto.getTeacher());
		return tss;
	}
}
