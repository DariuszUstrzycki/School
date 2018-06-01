package pl.ust.school.teacher;

import java.time.LocalDate;
import java.util.Set;

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
import pl.ust.school.tss.TSS;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {

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
	private Set<TSS> tsses;

	public boolean isNew() {
		return this.id < 1;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/*
	 * 
	 * 
	 * 
	 * A method annotated with @Builder (from now on called the target) causes the
	 * following 7 things to be generated:
	 * 
	 * An inner static class named FooBuilder, with the same type arguments as the
	 * static method (called the builder). In the builder: One private non-static
	 * non-final field for each parameter of the target. In the builder: A package
	 * private no-args empty constructor. In the builder: A 'setter'-like method for
	 * each parameter of the target: It has the same type as that parameter and the
	 * same name. It returns the builder itself, so that the setter calls can be
	 * chained, as in the above example. In the builder: A build() method which
	 * calls the method, passing in each field. It returns the same type that the
	 * target returns. In the builder: A sensible toString() implementation. In the
	 * class containing the target: A builder() method, which creates a new instance
	 * of the builder.
	 * 
	 * public static class Builder {
	 * 
	 * private TeacherDto teacherDto = new TeacherDto();
	 * 
	 * .......
	 * 
	 * public Builder withBirthDate(LocalDate birthDate) { teacherDto.birthDate =
	 * birthDate; return this; }
	 * 
	 * public Builder withTSSs(Set<TSS> tsss) { teacherDto.tsss = tsss; return this;
	 * }
	 * 
	 * public TeacherDto build() { return teacherDto; }
	 * 
	 * }
	 */

}
