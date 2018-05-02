
package pl.ust.school.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.NaturalId;


@MappedSuperclass
public class NamedEntity extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty // @NotNull +  @Size(min=1)
	// @NaturalId To be added but first I must learn how to handle org.hibernate.HibernateException:: An immutable natural identifier  of entity com.pramati.model.Person was altered 
	// Natural ids are immutable by default. We can have at-most one natural id defined for an entity. When Hibernate sees natural-id tag in an entity mapping file, it automatically creates unique and not-null constraints 
    // https://prasanthnath.wordpress.com/2013/04/24/natural-ids-in-hibernate/
	@Column(unique = true, nullable=false)
	@org.hibernate.annotations.ColumnDefault("not assigned")
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
