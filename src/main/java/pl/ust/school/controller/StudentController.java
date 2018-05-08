package pl.ust.school.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.entity.SchoolForm;
import pl.ust.school.entity.Student;
import pl.ust.school.repository.SchoolFormRepository;
import pl.ust.school.repository.StudentRepository;

@Controller
@RequestMapping("student") 
public class StudentController {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/studentForm";
	private static final String LIST_VIEW = "detailsNLists/studentList";
	private static final String DETAILS_VIEW = "detailsNLists/studentDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private SchoolFormRepository schoolFormRepo;
	
	//////////////////////////////////////////////////////////
	
	@ModelAttribute("schoolFormItems")
    public Collection<SchoolForm> populateSchoolFormItems() {
        return (Collection<SchoolForm>) this.schoolFormRepo.findAll();
    }
		
	//////////////////////////////////////////////////////////
	
	@InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	
	////////////////////////////SAVE ////////////////////////////
	
	@GetMapping("/save")
	public String showForm(Student student, Model model) {
		model.addAttribute("entityName", "student"); 
		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	
	@PostMapping("/save")
	public String saveStudent(@Valid Student student, BindingResult result, Model model) {
		
		model.addAttribute("entityName", "student"); 
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.err.println(error);
			}
			return CREATE_OR_UPDATE_FORM_VIEW; 
		}
		
		this.studentRepo.save(student);	
		return "redirect:/student/view/" + student.getId(); 
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/list")
	public String listStudents(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute("studentItems", this.studentRepo.findAll());
		model.addAttribute("entityName", "student");
		return LIST_VIEW;
	}

	//////////////////////////// VIEW ONE ////////////////////////////
	@RequestMapping("view/{id}")
	public String viewStudent(@PathVariable long id, Model model) {

		Set<Student> studentItems = new HashSet<>();
		Optional<Student> opt = (Optional<Student>) this.studentRepo.findById(id);
		opt.ifPresent(student -> studentItems.add(student));

		model.addAttribute("studentItems", studentItems);
		return DETAILS_VIEW;
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "student");
		return CONFIRM_DELETE_VIEW;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteStudent(@PathVariable long id) {

		Optional<Student> opt = (Optional<Student>) studentRepo.findById(id);
		opt.ifPresent(student -> {
			
			student.setSchoolForm(null);;
			this.studentRepo.deleteById(id);

		});

		return "redirect:/student/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	// Neither BindingResult nor plain target object for bean name 'student' available as request attribute
	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {
		
		Optional<Student> opt = (Optional<Student>) studentRepo.findById(id);
		opt.ifPresent(student -> {
			model.addAttribute(student);

		});
		
		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateStudent(@Valid Student student, BindingResult result, @PathVariable long id, Model model) {

		if (result.hasErrors()) {
			return CREATE_OR_UPDATE_FORM_VIEW;
		} else {
			System.out.println("--------UPDATE POST: student: " + student);
			student.setId(id);
			this.studentRepo.save(student);
			System.out.println("--------UPDATE POST: after saving student ");

			return "redirect:/student/view/" + id;
		}
		
	}

}
