package pl.ust.school.controller;

import java.util.Optional;

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
import pl.ust.school.service.SchoolformService;
import pl.ust.school.service.StudentService;
import pl.ust.school.service.TSSService;

@Controller
@RequestMapping("schoolform")
public class SchoolformController {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "schoolform/schoolformForm";
	private static final String LIST_VIEW = "schoolform/schoolformList";
	private static final String DETAILS_VIEW = "schoolform/schoolformDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";

	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "schoolformItems";
	private static final String COLLECTION_OF_STUDENTS_NAME = "studentItems";
	private static final String COLLECTION_OF_TEACHERSTUDENTS_NAME = "tSSItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "schoolform";

	@Autowired
	private SchoolformService schoolformService;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TSSService tSSService;

	//////////////////////////// before each ////////////////////////////

	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}

	///////////////////////////////// ////////////////////////////

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//////////////////////////// SAVE ////////////////////////////

	@GetMapping("/save")
	public String showForm(SchoolformDto schoolformDto, Model model) {
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/save")
	public String saveSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		}

		this.schoolformService.createSchoolform(schoolformDto);
		return "redirect:/schoolform/list";
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listSchoolforms(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.schoolformService.getAllSchoolformDtos());
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSchoolform(@PathVariable long id, Model model) {
		
		Optional<SchoolformDto> opt = this.schoolformService.getSchoolformDtoById(id);

		if (opt.isPresent()) {
			model.addAttribute("schoolformDto", opt.get());
			model.addAttribute(COLLECTION_OF_STUDENTS_NAME, this.studentService.getStudentBySchoolformId(id));
			model.addAttribute(COLLECTION_OF_TEACHERSTUDENTS_NAME, this.tSSService.getAllTSSs());
			//
		} else {
			throw new RecordNotFoundException("No school form with id " + id + " has been found.");
		}

		return DETAILS_VIEW;
	}

	//////////////////////////// DELETE //////////////////////////// Subjects taught:  subjectsTaughtFrag.jspf  

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id) {
		return CONFIRM_DELETE_VIEW;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSchoolform(@PathVariable long id) {

		this.schoolformService.deleteSchoolform(id);
		return "redirect:/schoolform/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	
	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {

		Optional<SchoolformDto> opt = this.schoolformService.getSchoolformDtoById(id);
		if (opt.isPresent()) {
			SchoolformDto schoolformDto = opt.get();
			model.addAttribute("schoolformDto", schoolformDto);
			model.addAttribute("notTaughTSSs", this.schoolformService.getNotTaughtTSSs(schoolformDto));
			model.addAttribute(COLLECTION_OF_STUDENTS_NAME, this.studentService.getStudentBySchoolformId(id));
			model.addAttribute(COLLECTION_OF_TEACHERSTUDENTS_NAME, this.tSSService.getAllTSSs());
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}

		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("notTaughTSSs", this.schoolformService.getNotTaughtTSSs(schoolformDto));
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			schoolformDto.setId(id);
			this.schoolformService.createSchoolform(schoolformDto);
			return "redirect:/schoolform/view/" + id;
		}

	}

	////////////////////////// remove/add new tSS from/to this scholform //////////

	@GetMapping("/{schoolformId}/tSS/{tSSId}/remove")
	private String removeTSSFromSchoolForm(@PathVariable long schoolformId, @PathVariable long tSSId) {

		this.schoolformService.removeSchoolformFromTSS(schoolformId, tSSId);
		return "redirect:/schoolform/update/" + schoolformId;
	}

	@GetMapping("/{schoolformId}/tSS/{tSSId}/add")
	private String addSubjectToTeacher(@PathVariable long schoolformId, @PathVariable long tSSId) {

		this.schoolformService.addSchoolformToTSS(schoolformId, tSSId);
		return "redirect:/schoolform/update/" + schoolformId;
	}

	////////////////////// exception handling ////////////////////////////////////

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return DETAILS_VIEW;
	}

}
