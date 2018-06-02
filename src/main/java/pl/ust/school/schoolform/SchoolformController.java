package pl.ust.school.schoolform;

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

import pl.ust.school.student.StudentService;
import pl.ust.school.system.RecordNotFoundException;
import pl.ust.school.tss.TSSService;

@Controller
@RequestMapping("schoolform")
public class SchoolformController {

	private static final String VIEW_CREATE_OR_UPDATE_FORM = "schoolform/schoolformForm";
	private static final String VIEW_LIST = "schoolform/schoolformList";
	private static final String VIEW_DETAILS = "schoolform/schoolformDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";

	private static final String NAME_COLLECTION_OF_SCHOOLFORMS = "schoolformItems";
	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
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
	public String showCreateForm(SchoolformDto schoolformDto, Model model) {
		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/save")
	public String saveSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_UPDATE_FORM;
		}

		this.schoolformService.createSchoolform(schoolformDto);
		return "redirect:/schoolform/list";
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listSchoolforms(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(NAME_COLLECTION_OF_SCHOOLFORMS, this.schoolformService.getAllSchoolformDtos());
		return VIEW_LIST;
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSchoolform(@PathVariable long id, Model model) {
		
		Optional<SchoolformDto> opt = this.schoolformService.getSchoolformDtoById(id);

		if (opt.isPresent()) {
			model.addAttribute("schoolformDto", opt.get());
			model.addAttribute(NAME_COLLECTION_OF_STUDENTS, this.studentService.getStudentsBySchoolformId(id));
			model.addAttribute(COLLECTION_OF_TEACHERSTUDENTS_NAME, this.tSSService.getAllTSSs());
			//
		} else {
			throw new RecordNotFoundException("No school form with id " + id + " has been found.");
		}

		return VIEW_DETAILS;
	}

	//////////////////////////// DELETE //////////////////////////// Subjects taught:  subjectsTaughtFrag.jspf  

	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable long id) {
		return VIEW_CONFIRM_DELETE;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSchoolform(@PathVariable long id) {

		this.schoolformService.deleteSchoolform(id);
		return "redirect:/schoolform/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, Model model) {

		Optional<SchoolformDto> opt = this.schoolformService.getSchoolformDtoById(id);
		if (opt.isPresent()) {
			SchoolformDto schoolformDto = opt.get();
			model.addAttribute("schoolformDto", schoolformDto);
			model.addAttribute("notTaughTSSs", this.schoolformService.getNotTaughtTSSs(schoolformDto));
			model.addAttribute(NAME_COLLECTION_OF_STUDENTS, this.studentService.getStudentsBySchoolformId(id));
			model.addAttribute(COLLECTION_OF_TEACHERSTUDENTS_NAME, this.tSSService.getAllTSSs());
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}

		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/update/{id}")
	public String updateSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("notTaughTSSs", this.schoolformService.getNotTaughtTSSs(schoolformDto));
			return VIEW_CREATE_OR_UPDATE_FORM;
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
		return VIEW_DETAILS;
	}

}
