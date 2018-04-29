
package pl.ust.school.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;


@MappedSuperclass
public class Person extends IdEntity {

	private static final long serialVersionUID = 1L;
	
    @NotEmpty // @NotNull +  @Size(min=1)
    @Column(name = "first_name")
    private String firstName;
    
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	@Override
	public String toString() {
		return super.toString() + "[firstName=" + firstName + ", lastName=" + lastName + "]";
	}
    
    

}
