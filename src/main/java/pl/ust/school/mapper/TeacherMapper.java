package pl.ust.school.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pl.ust.school.dto.TeacherDto;
import pl.ust.school.dto.TeacherDto;
import pl.ust.school.entity.Teacher;
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
					.build();
	}
	
	public Optional<TeacherDto> toDTO(Optional<Teacher> opt) {
		if(opt.isPresent()) {
			return Optional.of(toDTO(opt.get()));
		} else {
			return Optional.empty();
		}
	}

	public Teacher fromDTO(TeacherDto dto) {
		Teacher teacher = new Teacher();
		teacher.setId(dto.getId());
		teacher.setDeleted(dto.getIsDeleted());
		teacher.setFirstName(dto.getFirstName());
		teacher.setLastName(dto.getLastName());
		teacher.setAddress(dto.getAddress());
		teacher.setBirthDate(dto.getBirthDate());
		teacher.setEmail(dto.getEmail());
		teacher.setPassword(dto.getPassword());
		teacher.setTelephone(dto.getTelephone());
		return teacher;
	}

}
