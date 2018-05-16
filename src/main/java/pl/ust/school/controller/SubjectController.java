package pl.ust.school.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.ust.school.entity.Subject;
import pl.ust.school.repository.SubjectRepository;

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
	private SubjectRepository subjectRepo;

	//////////////////////////////////////////////////////////

	/*
	 * nothing to populate the model first ?!
	 * 
	 * @ModelAttribute("schoolformItems") public Collection<SchoolForm>
	 * populateSchoolFormItems() { return (Collection<SchoolForm>)
	 * this.schoolFormRepo.findAll(); }
	 */

	//////////////////////////////////////////////////////////

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//////////////////////////// SAVE ////////////////////////////

	@GetMapping("/save")
	public String showForm(Subject subject, Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/save")
	public String saveSubject(@Valid Subject subject, BindingResult result, Model model) {

		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.err.println(error);
			}
			return CREATE_OR_UPDATE_FORM_VIEW;
		}
		
		this.subjectRepo.save(subject);
		return "redirect:/subject/view/" + subject.getId();
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listSubjects(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_SUBJECTS_NAME, this.subjectRepo.findAll());
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////
	@RequestMapping("view/{id}")
	public String viewSubject(@PathVariable long id, Model model) {

		Set<Subject> subjectItems = new HashSet<>();
		Optional<Subject> opt = this.subjectRepo.findById(id);
		
		if(opt.isPresent()) {
			subjectItems.add(opt.get());
			model.addAttribute(COLLECTION_OF_SUBJECTS_NAME, subjectItems);
		} else {
			throw new ItemNotFoundException("No subject with id " + id + " has been found.");
		}
		
		return DETAILS_VIEW;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return CONFIRM_DELETE_VIEW;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSubject(@PathVariable long id) {
		//  nie usuwać SUBJECTS!!!
		Optional<Subject> opt = subjectRepo.findById(id);
		opt.ifPresent(subject -> {
			/*
			for (Subject subject : subject.getSubjectSubjects()) {
				subject.removeSubjectSubject(subjectsubject);
			}

			this.subjectRepo.deleteById(id);*/

		});

		return "redirect:/subject/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	// Neither BindingResult nor plain target object for bean name 'subject'
	//////////////////////////// available as request attribute
	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {

		Optional<Subject> opt = subjectRepo.findById(id);
		opt.ifPresent(model::addAttribute);
		
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateSubject(@Valid  Subject subject, BindingResult result, @PathVariable long id) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			subject.setId(id);
			this.subjectRepo.save(subject);
			return "redirect:/subject/view/" + id;
		}

	}
	
	////////////////////// exception handling ////////////////////////////////////
	
	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String itemNotFoundHandler(ItemNotFoundException ex, Model model) {
		System.err.println("----------------ItemNotFoundException");
		model.addAttribute("notFound", ex.getMessage());
		
		return DETAILS_VIEW;
    }

}
