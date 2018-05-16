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

import pl.ust.school.entity.Teacher;
import pl.ust.school.entity.TeacherSubject;
import pl.ust.school.repository.TeacherRepository;

@Controller
@RequestMapping("teacher")
public class TeacherController {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/teacherForm";
	private static final String LIST_VIEW = "detailsNLists/teacherList";
	private static final String DETAILS_VIEW = "detailsNLists/teacherDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	private static final String COLLECTION_OF_TEACHERS_NAME = "teacherItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "teacher";

	@Autowired
	private TeacherRepository teacherRepo;
	
	//////////////////////////////////////////////////////////
	
	/*
	 *  nothing to populate the model first ?!
	 * @ModelAttribute("schoolformItems")
    public Collection<SchoolForm> populateSchoolFormItems() {
        return (Collection<SchoolForm>) this.schoolFormRepo.findAll();
    }*/
		
	//////////////////////////////////////////////////////////
	
	@InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	
	////////////////////////////SAVE ////////////////////////////
	
	@GetMapping("/save")
	public String showForm(Teacher teacher, Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE); 
		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	
	@PostMapping("/save")
	public String saveTeacher(@Valid Teacher teacher, BindingResult result, Model model) {
		
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE); 
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}
			return CREATE_OR_UPDATE_FORM_VIEW; 
		}
		
		this.teacherRepo.save(teacher);	
		return "redirect:/teacher/view/" + teacher.getId(); 
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listTeachers(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_TEACHERS_NAME, this.teacherRepo.findAll());
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////
	@RequestMapping("view/{id}")
	public String viewTeacher(@PathVariable long id, Model model) {

		Set<Teacher> teacherItems = new HashSet<>();
		Optional<Teacher> opt = this.teacherRepo.findById(id);
		
		if(opt.isPresent()) {
			teacherItems.add(opt.get());
			model.addAttribute(COLLECTION_OF_TEACHERS_NAME, teacherItems);
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

		Optional<Teacher> opt = teacherRepo.findById(id);
		opt.ifPresent(teacher -> {
			
			for(TeacherSubject teachersubject : teacher.getTeacherSubjects()) {
				teacher.removeTeacherSubject(teachersubject);
			}
			
			this.teacherRepo.deleteById(id);

		});

		return "redirect:/teacher/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	// Neither BindingResult nor plain target object for bean name 'teacher' available as request attribute
	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {
		
		Optional<Teacher> opt = teacherRepo.findById(id);
		opt.ifPresent(model::addAttribute);
		
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateTeacher(@Valid Teacher teacher, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			System.out.println("--------UPDATE POST: teacher: " + teacher);
			teacher.setId(id);
			this.teacherRepo.save(teacher);
			System.out.println("--------UPDATE POST: after saving teacher ");

			return "redirect:/teacher/view/" + id;
		}
	}
	
	//////////////////////exception handling ////////////////////////////////////
	
	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String itemNotFoundHandler(RecordNotFoundException ex, Model model) {
		System.err.println("----------------RecordNotFoundException");
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
