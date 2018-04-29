package pl.ust.school.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends StaffPerson {

	private static final long serialVersionUID = 1L;

}
