
package pl.ust.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    
	private static final long serialVersionUID = 1L;
	
    @NotEmpty // not null or empty
    @Column(name = "address")
    private String address;
    
    @NotEmpty
    @Column(name = "city")
    private String city;
    
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    @Column(name = "telephone")
    private String telephone;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return super.toString() + "Teacher [address=" + address + ", city=" + city + ", telephone=" + telephone + "]";
	}
	
    
    
	
}
