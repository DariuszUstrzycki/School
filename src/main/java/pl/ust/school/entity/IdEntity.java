
package pl.ust.school.entity;

import java.io.Serializable;

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
public class IdEntity implements Serializable {
   
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@Column(nullable=false)
	@org.hibernate.annotations.ColumnDefault("false")
	private boolean isDeleted;

    public boolean isNew() {
        return this.id == null;
    }
    
    
	
    
    
    

}
