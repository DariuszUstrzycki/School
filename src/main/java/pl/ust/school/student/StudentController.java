package pl.ust.school.student;

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

import pl.ust.school.schoolform.SchoolformDto;
import pl.ust.school.schoolform.SchoolformService;
import pl.ust.school.system.RecordNotFoundException;

@Controller
@RequestMapping("student")
public class StudentController {

	private static final String VIEW_CREATE_OR_UPDATE_FORM = "student/studentForm";
	private static final String VIEW_LIST = "student/studentList";
	private static final String VIEW_DETAILS = "student/studentDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";

	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
	private static final String NAME_COLLECTION_OF_SCHOOLFORMS = "schoolformItems";
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

	@ModelAttribute(NAME_COLLECTION_OF_SCHOOLFORMS)
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
	public String showCreateForm(StudentDto studentDto) {
		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/save")
	public String saveStudent(@Valid StudentDto studentDto, BindingResult result) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_UPDATE_FORM;
		}

		long id = this.studentService.createStudent(studentDto);
		return "redirect:/student/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listStudents(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(NAME_COLLECTION_OF_STUDENTS, this.studentService.getAllStudents());
		return VIEW_LIST;
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

		return VIEW_DETAILS;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable long id) {
		return VIEW_CONFIRM_DELETE;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteStudent(@PathVariable long id) {

		this.studentService.deleteStudent(id);
		return "redirect:/student/list";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, Model model) {

		Optional<StudentDto> opt = studentService.getStudentDtoById(id);
		opt.ifPresent(model::addAttribute);

		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/update/{id}")
	public String updateStudent(@Valid StudentDto studentDto, BindingResult result, @PathVariable long id) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_UPDATE_FORM;
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
		return VIEW_DETAILS;
	}

}
