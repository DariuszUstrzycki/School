package pl.ust.school.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.entity.SchoolForm;
import pl.ust.school.entity.Student;
import pl.ust.school.repository.SchoolFormRepository;
import pl.ust.school.repository.StudentRepository;

@Controller
@RequestMapping("schoolform") // endpoint should be read some config file
public class SchoolFormController {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/schoolformForm";
	private static final String LIST_VIEW = "detailsNLists/schoolformList";
	private static final String DETAILS_VIEW = "detailsNLists/schoolformDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "schoolformItems";
	private static final String COLLECTION_OF_STUDENTS_NAME = "studentItems";
	
	@Autowired
	private SchoolFormRepository schooFormRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	
	////////////////////////////SAVE ////////////////////////////
	
	@GetMapping("/save")
	public String showForm(SchoolForm form, Model model) {
		model.addAttribute("entityName", "schoolform"); // endpoint should be read some config file
		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	
	@PostMapping("/save")
	public String saveSchoolForm(@Valid SchoolForm form, BindingResult result, Model model) {
		
		model.addAttribute("entityName", "schoolform"); // endpoint should be read some config file
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}
			return CREATE_OR_UPDATE_FORM_VIEW; 
		}
		
		this.schooFormRepo.save(form);	
		return "redirect:/schoolform/view/" + form.getId(); 
	}
		
	////////////////////////////LIST ////////////////////////////
	
	@RequestMapping("/list")
	public String listSchoolForms(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.schooFormRepo. findAll());
		model.addAttribute("entityName", "schoolform");
		return LIST_VIEW;
	}
	
	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSchoolForm(@PathVariable long id, Model model) {

		Set<SchoolForm> schoolformItems = new HashSet<>();
		Optional<SchoolForm> opt = (Optional<SchoolForm>) this.schooFormRepo. findById(id);
		//opt.ifPresent(schoolForm -> schoolformItems.add(schoolForm)); 
		
		if(opt.isPresent()) {
			schoolformItems.add(opt.get());
			model.addAttribute("schoolformName", opt.get().getName());
			model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, schoolformItems);
			model.addAttribute(COLLECTION_OF_STUDENTS_NAME, studentRepo.findBySchoolForm_Id(id));
		} else {
			model.addAttribute("notFound", "No item with id " + id + " has been found!");
		}
		
		return DETAILS_VIEW; 
	}                        
	
	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "schoolform");
		return CONFIRM_DELETE_VIEW;
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSchoolForm(@PathVariable long id) {
		
		Optional<SchoolForm> opt = (Optional<SchoolForm>) schooFormRepo.findById(id);
		opt.ifPresent(schoolForm -> {

			for (Student student : schoolForm.getStudents()) {
				schoolForm.removeStudent(student);
			}

			this.schooFormRepo.deleteById(id);

		});
		
		return "redirect:/schoolform/list";
	}

	//////////////////////////// UPDATE ////////////////////////////
	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {
		Optional<SchoolForm> opt = (Optional<SchoolForm>) schooFormRepo.findById(id);
		opt.ifPresent(schoolForm -> {
			model.addAttribute(schoolForm);
			
		});

		return CREATE_OR_UPDATE_FORM_VIEW;
	}

	@PostMapping("/update/{id}")
	public String updateSchoolForm(@Valid SchoolForm form, BindingResult result, @PathVariable long id, Model model) {
		
		if (result.hasErrors()) {
	         return CREATE_OR_UPDATE_FORM_VIEW;
	     } else {
	    	 form.setId(id);
	         this.schooFormRepo.save(form);
	         return "redirect:/schoolform/view/" + id;
	     }
		
	}

}
