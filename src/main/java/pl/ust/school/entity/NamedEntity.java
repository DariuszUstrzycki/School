
package pl.ust.school.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;


@MappedSuperclass
public class NamedEntity extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty // @NotNull +  @Size(min=1)
    @Column
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "[name=" + name + "]";
	}
    

}
