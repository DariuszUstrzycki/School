package pl.ust.school.controller;

import java.util.Collection;
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
import pl.ust.school.dto.StudentDto;
import pl.ust.school.service.SchoolformService;
import pl.ust.school.service.StudentService;

@Controller
@RequestMapping("student")
public class StudentController {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "student/studentForm";
	private static final String LIST_VIEW = "student/studentList";
	private static final String DETAILS_VIEW = "student/studentDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";

	private static final String COLLECTION_OF_STUDENTS_NAME = "studentItems";
	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "schoolformItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "student";

	@Autowired
	private StudentService studentService;

	@Autowired
	private SchoolformService schoolformService;

	//////////////////////////// before each ////////////////////////////

	@ModelAttribute
	public void addEntityName(Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
	}

	@ModelAttribute(COLLECTION_OF_SCHOOLFORMS_NAME)
	public Collection<SchoolformDto> populateSchoolformItems() {
		return this.schoolformService.getAllSchoolformDtos();
	}

	//////////////////////////////////////////////////////////

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//////////////////////////// SAVE ////////////////////////////

	@GetMapping("/save")
	public String showForm(StudentDto studentDto) {
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/save")
	public String saveStudent(@Valid StudentDto studentDto, BindingResult result) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		}

		long id = this.studentService.createStudent(studentDto);
		return "redirect:/student/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listStudents(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_STUDENTS_NAME, this.studentService.getAllStudents());
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////
	@RequestMapping("view/{id}")
	public String viewStudent(@PathVariable long id, Model model) {

		Optional<StudentDto> opt = this.studentService.getStudentDtoById(id);

		if (opt.isPresent()) {
			StudentDto studentDto = opt.get();
			model.addAttribute("studentDto", studentDto);
		} else {
			throw new RecordNotFoundException("No student with id " + id + " has been found.");
		}

		return DETAILS_VIEW;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id) {
		return CONFIRM_DELETE_VIEW;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteStudent(@PathVariable long id) {

		this.studentService.deleteStudent(id);
		return "redirect:/student/list";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {

		Optional<StudentDto> opt = studentService.getStudentDtoById(id);
		opt.ifPresent(model::addAttribute);

		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateStudent(@Valid StudentDto studentDto, BindingResult result, @PathVariable long id) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			studentDto.setId(id);
			this.studentService.createStudent(studentDto);
			return "redirect:/student/view/" + id;
		}

	}

	@GetMapping("/{studentId}/removeFrom/{schoolformId}")
	public String removeStudentFromSchoolform(@PathVariable long studentId, @PathVariable long schoolformId) {

		this.studentService.removeStudentFromSchoolform(studentId);
		return "redirect:/schoolform/view/" + schoolformId;
	}

	////////////////////// exception handling ////////////////////////////////////

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return DETAILS_VIEW;
	}

}
