package pl.ust.school.controller;

import java.util.Optional;

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

import pl.ust.school.dto.TeacherDto;
import pl.ust.school.service.SubjectService;
import pl.ust.school.service.TeacherService;

@Controller
@RequestMapping("teacher")
public class TeacherController {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/teacherForm";
	private static final String LIST_VIEW = "detailsNLists/teacherList";
	private static final String DETAILS_VIEW = "detailsNLists/teacherDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	private static final String COLLECTION_OF_SUBJECTS_NAME = "subjectItems";
	private static final String COLLECTION_OF_TEACHERS_NAME = "teacherItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "teacher";

	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private SubjectService subjectService;
	
	//////////////////////////////////////////////////////////
	
	@InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	
	////////////////////////////SAVE ////////////////////////////
	
	@GetMapping("/save")
	public String showForm(TeacherDto teacherDto, Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE); 
		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	
	@PostMapping("/save")
	public String saveTeacher(@Valid TeacherDto teacherDto, BindingResult result, Model model) {
		
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE); 
		
		if(result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println(error);
			}
			
			return CREATE_OR_UPDATE_FORM_VIEW; 
		}
		
		long id = this.teacherService.createTeacher(teacherDto);	
		return "redirect:/teacher/view/" + id; 
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listTeachers(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_TEACHERS_NAME, this.teacherService.getAllTeachers());
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////
	@RequestMapping("view/{id}")
	public String viewTeacher(@PathVariable long id, Model model) {

		Optional<TeacherDto> opt = this.teacherService.getTeacherById(id);
		
		if (opt.isPresent()) {
			model.addAttribute("teacherDto", opt.get());
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
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
	public String deleteTeacher(@PathVariable long id) {

		this.teacherService.deleteTeacher(id);

		return "redirect:/teacher/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	
	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {
		
		Optional<TeacherDto> opt = this.teacherService.getTeacherById(id);
		if (opt.isPresent()) {
			TeacherDto teacherDto = opt.get();
			model.addAttribute("teacherDto", teacherDto);
			
			model.addAttribute("remainingSubjects", 
					this.teacherService.getSubjectsNotTaughtByTeacher(teacherDto, this.subjectService.getAllSubjects()));
			
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}
		
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateTeacher(@Valid TeacherDto teacherDto, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			teacherDto.setId(id);
			this.teacherService.createTeacher(teacherDto);
			return "redirect:/teacher/view/" + id;
		}
	}

	////////////////////////// remove/add new subject to this teacher ////////////////////////// /////////////////////
	

	@GetMapping("/{teacherId}/subject/{teacherSubjectId}/remove")
	private String removeSubjectFromTeacher(@PathVariable long teacherId, @PathVariable long teacherSubjectId,
			Model model) {

		this.teacherService.removeTeacherSubject(teacherId, teacherSubjectId);
		return "redirect:/teacher/update/" + teacherId;
	}

	@GetMapping("/{teacherId}/subject/{subjectId}/add")
	private String addSubjectToTeacher(@PathVariable long teacherId, @PathVariable long subjectId, Model model) {

		this.teacherService.addTeacherSubject(teacherId, subjectId);
		return "redirect:/teacher/update/" + teacherId;
	}

	//////////////////////exception handling ////////////////////////////////////
	
	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return DETAILS_VIEW;
    }
	/* TODO
	@ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    private String integrityConstraintViolationHandler(MySQLIntegrityConstraintViolationException ex, Model model) {
		model.addAttribute("notFound", "Problem while saving to the database. " + ex.getErrorCode() + ", " + ex.getMessage());
		System.err.println("----------------MySQLIntegrityConstraintViolationException");
		return CREATE_OR_UPDATE_FORM_VIEW;
    }*/

}
