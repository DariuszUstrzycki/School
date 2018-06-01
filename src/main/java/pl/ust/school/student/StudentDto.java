package pl.ust.school.student;

import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.schoolform.Schoolform;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

	private long id;
	private boolean isDeleted;

	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Email
	private String email;
	@NotEmpty
	private String password;
	@NotEmpty
	private String address;
	@NotEmpty
	@Digits(fraction = 0, integer = 12)
	private String telephone;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private LocalDate birthDate;
	private Schoolform schoolform;
	
	public boolean isNew() {
        return this.id < 1;
    }
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(boolean isDeleted) {
		 this.isDeleted = isDeleted;
	}
}
