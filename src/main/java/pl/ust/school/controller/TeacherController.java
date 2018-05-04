package pl.ust.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.ust.school.repository.SubjectRepository;
import pl.ust.school.repository.TeacherRepository;

@Controller
@RequestMapping("techer") 
public class TeacherController {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/teacherForm";
	private static final String LIST_VIEW = "detailsNLists/teacherList";
	private static final String DETAILS_VIEW = "detailsNLists/teacherDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	@Autowired
	private TeacherRepository teacherRepo;

}
