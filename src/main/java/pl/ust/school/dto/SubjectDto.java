package pl.ust.school.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.school.entity.TSS;

@Builder 
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubjectDto {

	
	private long id;
	private boolean isDeleted;
	@NotEmpty
	private String name;
	private Set<TSS> tSSs;  
	
	public boolean isNew() {
        return this.id < 1;
    }
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public boolean setIsDeleted(boolean isDeleted) {
		return this.isDeleted = isDeleted;
	}
	
	
}
