
package pl.ust.school.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@MappedSuperclass
@Getter @Setter @NoArgsConstructor
@ToString(includeFieldNames = false, callSuper=true, exclude= {"address", "telephone", "birthDate"} )
public class Person extends IdEntity {

	private static final long serialVersionUID = 1L;
	
    @NotEmpty // @NotNull +  @Size(min=1)
    @Column(name = "first_name", nullable=false)
    private String firstName;
    
    @NotEmpty
    @Column(name = "last_name", nullable=false)
    private String lastName;
    
    @Email
    @Column(unique = true, nullable=false)
    private String email;
    
    @NotEmpty
    @Column(nullable=false)
    private String password;
    
    @NotEmpty // not null or empty
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
    

}
