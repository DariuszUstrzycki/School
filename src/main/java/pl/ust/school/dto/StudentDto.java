package pl.ust.school.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import pl.ust.school.entity.SchoolForm;

@Builder @Getter
public class StudentDto {

	private Long id;
	private Boolean isDeleted;
	private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String telephone;    
    private LocalDate birthDate;
    private SchoolForm schoolForm;
}
