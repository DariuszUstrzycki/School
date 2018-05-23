package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.TeacherDto;
import pl.ust.school.entity.Teacher;

@Component
public class TeacherMapper {
	
	public TeacherDto toDTO(Teacher teacher){
		return TeacherDto.builder()
					.id(teacher.getId())
					.isDeleted(teacher.isDeleted())
					.firstName(teacher.getFirstName())
					.lastName(teacher.getLastName())
					.address(teacher.getAddress())
					.birthDate(teacher.getBirthDate())
					.email(teacher.getEmail())
					.password(teacher.getPassword())
					.telephone(teacher.getTelephone())
					.teacherSubjectSchforms(teacher.getTeacherSubjectSchforms())
					.build();
	}
	
	public Optional<TeacherDto> toDTO(Optional<Teacher> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}

	public Teacher fromDTO(TeacherDto teacherDto) {
		Teacher teacher = new Teacher();
		teacher.setId(teacherDto.getId());
		teacher.setDeleted(teacherDto.getIsDeleted());
		teacher.setFirstName(teacherDto.getFirstName());
		teacher.setLastName(teacherDto.getLastName());
		teacher.setAddress(teacherDto.getAddress());
		teacher.setBirthDate(teacherDto.getBirthDate());
		teacher.setEmail(teacherDto.getEmail());
		teacher.setPassword(teacherDto.getPassword());
		teacher.setTelephone(teacherDto.getTelephone());
		teacher.setTeacherSubjectSchforms(teacherDto.getTeacherSubjectSchforms());
		return teacher;
	}

}
