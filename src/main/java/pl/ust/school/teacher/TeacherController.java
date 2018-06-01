package pl.ust.school.teacher;

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

import pl.ust.school.system.RecordNotFoundException;

@Controller
@RequestMapping("teacher")
public class TeacherController {

	private static final String VIEW_CREATE_OR_UPDATE_FORM = "teacher/teacherForm";
	private static final String VIEW_LIST = "teacher/teacherList";
	private static final String VIEW_DETAILS = "teacher/teacherDetails";
	private static final String VIEW_CONFIRM_DELETE = "forms/confirmDelete";

	private static final String NAME_COLLECTION_OF_TEACHERS = "teacherItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "teacher";

	@Autowired
	private TeacherService teacherService;

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
	public String showCreateForm(TeacherDto teacherDto) {
		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/save")
	public String saveTeacher(@Valid TeacherDto teacherDto, BindingResult result) {

		if (result.hasErrors()) {
			return VIEW_CREATE_OR_UPDATE_FORM;
		}

		long id = this.teacherService.createTeacher(teacherDto);
		return "redirect:/teacher/view/" + id;
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listTeachers(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(NAME_COLLECTION_OF_TEACHERS, this.teacherService.getAllTeacherDtos());
		return VIEW_LIST;
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewTeacher(@PathVariable long id, Model model) {

		Optional<TeacherDto> opt = this.teacherService.getTeacherDtoById(id);

		if (opt.isPresent()) {
			model.addAttribute("teacherDto", opt.get());
		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}

		return VIEW_DETAILS;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable long id) {
		return VIEW_CONFIRM_DELETE;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteTeacher(@PathVariable long id) {
		this.teacherService.deleteTeacher(id);
		return "redirect:/teacher/list";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable long id, Model model) {

		Optional<TeacherDto> opt = this.teacherService.getTeacherDtoById(id);
		if (opt.isPresent()) {
			TeacherDto teacherDto = opt.get();
			model.addAttribute("teacherDto", teacherDto);
			model.addAttribute("notTaughSubjects", this.teacherService.getNotTaughtSubjects(teacherDto));

		} else {
			throw new RecordNotFoundException("No teacher with id " + id + " has been found.");
		}

		return VIEW_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/update/{id}")
	public String updateTeacher(@Valid TeacherDto teacherDto, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
		    model.addAttribute("notTaughSubjects", this.teacherService.getNotTaughtSubjects(teacherDto));
			return VIEW_CREATE_OR_UPDATE_FORM;
		} else {
			teacherDto.setId(id);
			this.teacherService.createTeacher(teacherDto);
			return "redirect:/teacher/view/" + id;
		}
	}

	////////////////////////// remove/add new subject to this teacher //////////

	@GetMapping("/{teacherId}/remove/{tSSId}")
	private String removeSubjectFromTeacher(@PathVariable long teacherId, @PathVariable long tSSId) {

		this.teacherService.removeTSS(tSSId); // completely
		return "redirect:/teacher/update/" + teacherId;
	}

	@GetMapping("/{teacherId}/subject/{subjectId}/add")
	private String addSubjectToTeacher(@PathVariable long teacherId, @PathVariable long subjectId) {

		this.teacherService.addTSS(teacherId, subjectId);
		return "redirect:/teacher/update/" + teacherId;
	}

	////////////////////// exception handling ////////////////////////////////////

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return VIEW_DETAILS;
	}

}
