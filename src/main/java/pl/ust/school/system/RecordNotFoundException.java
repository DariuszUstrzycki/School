package pl.ust.school.system;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Record not found") //
public class RecordNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	
}
