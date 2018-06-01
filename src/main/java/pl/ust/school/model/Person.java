
package pl.ust.school.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString(includeFieldNames = false, callSuper = true, exclude = { "address", "telephone", "birthDate" })
public class Person extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty // @NotNull + @Size(min=1)
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@NaturalId(mutable = true)
	@Email
	@Column(unique = true, nullable = false)
	private String email;

	@NotEmpty
	@Column(nullable = false)
	private String password;

	@NotEmpty 
	@Column
	private String address;

	@NotEmpty
	@Digits(fraction = 0, integer = 12)
	@Column
	private String telephone;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, email, password, address, telephone, birthDate);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Person person = (Person) o;
		return Objects.equals(this.firstName, person.firstName) && Objects.equals(this.lastName, person.lastName)
				&& Objects.equals(this.email, person.email) && Objects.equals(this.password, person.password)
				&& Objects.equals(this.address, person.address) && Objects.equals(this.telephone, person.telephone)
				&& Objects.equals(this.birthDate, person.birthDate);
	}

}
