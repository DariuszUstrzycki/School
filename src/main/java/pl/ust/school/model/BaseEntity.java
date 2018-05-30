
package pl.ust.school.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor @ToString
public class BaseEntity implements Serializable {
   
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotNull
	@Column(nullable=false)
	@org.hibernate.annotations.ColumnDefault("false")
	private boolean isDeleted;

    public boolean isNew() {
        return this.id < 1;
    }

	@Override
	public int hashCode() {
		return Objects.hash( id );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        BaseEntity idEntity = (BaseEntity) o;
        return Objects.equals( this.id, idEntity.id );
	}
    
    

}
