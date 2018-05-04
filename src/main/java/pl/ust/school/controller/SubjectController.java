package pl.ust.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.ust.school.repository.StudentRepository;
import pl.ust.school.repository.SubjectRepository;

@Controller
@RequestMapping("subject") 
public class SubjectController {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/subjectForm";
	private static final String LIST_VIEW = "detailsNLists/subjectList";
	private static final String DETAILS_VIEW = "detailsNLists/subjectDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	@Autowired
	private SubjectRepository subjectRepo;

}
