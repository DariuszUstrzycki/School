package pl.ust.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.service.TSSService;

@Controller
@RequestMapping("/tss") 
public class TSSController {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/tssForm";
	private static final String LIST_VIEW = "detailsNLists/tssList";
	private static final String DETAILS_VIEW = "detailsNLists/tssDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";

	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "tssItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "Teacher/Subject/Schoolform";

	@Autowired
	private TSSService tssService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	//////////////////////////// before each ////////////////////////////
	
	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listTSSs(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.tssService.getAllTSSs());
		
		return LIST_VIEW;
	}
	
	@GetMapping("/schoolform/{id}")
	public String listTSSsBySchoolform(@PathVariable long id, 
										@RequestParam(defaultValue = "0", required = false) int min, Model model) {

		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.tssService.getTSSsBySchoolformId(id));
		return LIST_VIEW;
	}
	
	@GetMapping("/teacher/{id}")
	public String listTSSsByTeacher(@PathVariable long id, 
										@RequestParam(defaultValue = "0", required = false) int min, Model model) {

		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.tssService.getTSSsByTeacherId(id));
		return LIST_VIEW;
	}
	
	@GetMapping("/subject/{id}")
	public String listTSSsBySubject(@PathVariable long id, 
										@RequestParam(defaultValue = "0", required = false) int min, Model model) {

		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.tssService.getTSSsBySubjectId(id));
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return LIST_VIEW;
	}
	
}
