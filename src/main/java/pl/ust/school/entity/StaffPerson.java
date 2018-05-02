
package pl.ust.school.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;


@MappedSuperclass
public class StaffPerson extends IdEntity {

	private static final long serialVersionUID = 1L;
	
    @NotEmpty // @NotNull +  @Size(min=1)
    @Column(name = "first_name")
    private String firstName;
    
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;
    
    @NotEmpty // not null or empty
    @Column
    private String address;
        
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    @Column
    private String telephone;    
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    
    /////////////// getters and setters ///////////////////

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "StaffPerson [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + 
				", telephone=" + telephone + "]";
	}
    
    

    
    

}
