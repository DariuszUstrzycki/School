package pl.ust.school.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import pl.ust.school.dto.SubjectDto;
import pl.ust.school.service.SubjectService;

@Controller
@RequestMapping("subject")
public class SubjectController {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/subjectForm";
	private static final String LIST_VIEW = "detailsNLists/subjectList";
	private static final String DETAILS_VIEW = "detailsNLists/subjectDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";

	private static final String COLLECTION_OF_SUBJECTS_NAME = "subjectItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "subject";

	@Autowired
	private SubjectService subjectService;

	//////////////////////////// before each ////////////////////////////

	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}

	//////////////////////////////////////////////////////////

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//////////////////////////// SAVE ////////////////////////////

	@GetMapping("/save")
	public String showForm(SubjectDto subjectDto, Model model) {
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/save")
	public String saveSubject(@Valid SubjectDto subjectDto, BindingResult result) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		}

		long id = this.subjectService.createSubject(subjectDto);
		return "redirect:/subject/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listSubjects(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_SUBJECTS_NAME, this.subjectService.getAllSubjectDtos());
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSubject(@PathVariable long id, Model model) {

		Optional<SubjectDto> opt = this.subjectService.getSubjectDtoById(id);

		if (opt.isPresent()) {
			Set<SubjectDto> subjectItems = new HashSet<>();
			subjectItems.add(opt.get());
			model.addAttribute(COLLECTION_OF_SUBJECTS_NAME, subjectItems);
		} else {
			throw new RecordNotFoundException("No subject with id " + id + " has been found.");
		}

		return DETAILS_VIEW;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id) {
		return CONFIRM_DELETE_VIEW;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSubject(@PathVariable long id) {
		this.subjectService.deleteSubject(id);
		return "redirect:/subject/list";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {

		Optional<SubjectDto> subjectDto = this.subjectService.getSubjectDtoById(id);

		if (subjectDto.isPresent()) {
			model.addAttribute(subjectDto.get());
		}

		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateSubject(@Valid SubjectDto subjectDto, BindingResult result, @PathVariable long id) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			subjectDto.setId(id);
			this.subjectService.createSubject(subjectDto);
			return "redirect:/subject/view/" + id;
		}

	}

	////////////////////// exception handling ////////////////////////////////////

	@ExceptionHandler
	private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return DETAILS_VIEW;
	}

}
