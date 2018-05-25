package pl.ust.school.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.ust.school.dto.SchoolformDto;
import pl.ust.school.dto.TSSDto;
import pl.ust.school.service.SchoolformService;
import pl.ust.school.service.SubjectService;
import pl.ust.school.service.TSSService;
import pl.ust.school.service.TeacherService;

@Controller
@RequestMapping("/tss") 
public class TSSController {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/tssForm";
	private static final String LIST_VIEW = "detailsNLists/tssList";
	private static final String DETAILS_VIEW = "detailsNLists/tssDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";

	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "schoolformItems";
	private static final String COLLECTION_OF_SUBJECTS_NAME = "subjectItems";
	private static final String COLLECTION_OF_TEACHERS_NAME = "teacherItems";
	private static final String COLLECTION_OF_TSS_NAME = "tssItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "Teacher/Subject/Schoolform";

	@Autowired
	private TSSService tssService;
	
	@Autowired
	private SchoolformService schoolformService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private TeacherService teacherService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
		
	//////////////////////////// before each ////////////////////////////
	
	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}
	
	
////////////////////////////SAVE ////////////////////////////

	@GetMapping("/save")
	public String showForm(TSSDto tSSDto, Model model) {
		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.schoolformService.getAllSchoolforms());
		model.addAttribute(COLLECTION_OF_SUBJECTS_NAME, this.subjectService.getAllSubjects());
		model.addAttribute(COLLECTION_OF_TEACHERS_NAME, this.teacherService.getAllTeachers());
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/save")
	public String saveTSS(@Valid TSSDto tSSDto, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		}
		
		// check if such TSS exists here firsat!!!

		long id = this.tssService.createTSS(tSSDto);
		return "redirect:/tss/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listTSSs(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_TSS_NAME, this.tssService.getAllTSSs());
		
		return LIST_VIEW;
	}
	
	@GetMapping("/schoolform/{id}")
	public String listTSSsBySchoolform(@PathVariable long id, 
										@RequestParam(defaultValue = "0", required = false) int min, Model model) {

		model.addAttribute(COLLECTION_OF_TSS_NAME, this.tssService.getTSSsBySchoolformId(id));
		return LIST_VIEW;
	}
	
	@GetMapping("/teacher/{id}")
	public String listTSSsByTeacher(@PathVariable long id, 
										@RequestParam(defaultValue = "0", required = false) int min, Model model) {

		model.addAttribute(COLLECTION_OF_TSS_NAME, this.tssService.getTSSsByTeacherId(id));
		return LIST_VIEW;
	}
	
	@GetMapping("/subject/{id}")
	public String listTSSsBySubject(@PathVariable long id, 
										@RequestParam(defaultValue = "0", required = false) int min, Model model) {

		model.addAttribute(COLLECTION_OF_TSS_NAME, this.tssService.getTSSsBySubjectId(id));
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////
	
	@RequestMapping("view/{id}")
	public String viewTSSt(@PathVariable long id, Model model) {

		Optional<TSSDto> opt = this.tssService.getTSSById(id);

		if (opt.isPresent()) {
			Set<TSSDto> tssItems = new HashSet<>();
			tssItems.add(opt.get());
			model.addAttribute(COLLECTION_OF_TSS_NAME, tssItems);
		} else {
			throw new RecordNotFoundException("No TSS with id " + id + " has been found.");
		}

		return DETAILS_VIEW;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		return CONFIRM_DELETE_VIEW;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteTSSt(@PathVariable long id) {

		this.tssService.deleteTSS(id);

		return "redirect:/tss/list";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {

		Optional<TSSDto> opt = this.tssService.getTSSById(id);
		opt.ifPresent(model::addAttribute);
		
		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.schoolformService.getAllSchoolforms());
		model.addAttribute(COLLECTION_OF_SUBJECTS_NAME, this.subjectService.getAllSubjects());
		model.addAttribute(COLLECTION_OF_TEACHERS_NAME, this.teacherService.getAllTeachers());

		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateTSS(@Valid TSSDto tssDto, BindingResult result, @PathVariable long id,
			Model model) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			tssDto.setId(id);
			this.tssService.createTSS(tssDto);
			return "redirect:/tss/view/" + id;
		}
	}

	////////////////////// exception handling ////////////////////////////////////

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return DETAILS_VIEW;
	}

}
