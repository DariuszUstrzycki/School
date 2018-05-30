
package pl.ust.school.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@MappedSuperclass
@Getter @Setter @NoArgsConstructor
@ToString(includeFieldNames = false, callSuper=true )
public class NamedEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@NaturalId(mutable=true)
	@NotEmpty // @NotNull +  @Size(min=1)
	// @NaturalId To be added but first I must learn how to handle org.hibernate.HibernateException:: An immutable natural identifier  of entity com.pramati.model.Person was altered 
	// Natural ids are immutable by default. We can have at-most one natural id defined for an entity. When Hibernate sees natural-id tag in an entity mapping file, it automatically creates unique and not-null constraints 
    // https://prasanthnath.wordpress.com/2013/04/24/natural-ids-in-hibernate/
	@Column(unique = true, nullable = true) 
    private String name;
	
	@Override
	public int hashCode() {
		return Objects.hash( name );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        NamedEntity namedEntity = (NamedEntity) o;
        return Objects.equals( this.name, namedEntity.name );
	}

}
