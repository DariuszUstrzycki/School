package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="test_entities")
@Getter @Setter @NoArgsConstructor
public class TestEntity  {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
