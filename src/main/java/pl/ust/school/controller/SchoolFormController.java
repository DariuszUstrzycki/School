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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ust.school.entity.SchoolForm;
import pl.ust.school.entity.Student;
import pl.ust.school.repository.SchoolFormRepository;

@Controller
@RequestMapping("schoolform") // endpoint should be read some config file
public class SchoolFormController {
	
	@Autowired
	private SchoolFormRepository schoolformRepo;
	
	////////////////////////////SAVE ////////////////////////////
	
	@GetMapping("/save")
	public String showForm(SchoolForm form, Model model) {
		model.addAttribute("entityName", "schoolform"); // endpoint should be read some config file
		return "form/schoolformForm";
	}
	
	@PostMapping("/save")
	public String saveForm(@Valid SchoolForm form, BindingResult result, Model model) {
		
		model.addAttribute("entityName", "schoolform"); // endpoint should be read some config file
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}
			return "form/schoolformForm"; 
		}
		
		schoolformRepo.save(form);	
		return "redirect:/schoolform/list"; //  /schoolform/success
	}
	
	//////////////////////////// success ////////////////////////////
	
	@RequestMapping("/success")
	public String success(Model model) {
		model.addAttribute("successMsg", "New school form has been added");
		return "form/success";
	}
	
	////////////////////////////LIST ////////////////////////////
	
	@RequestMapping("/list")
	public String listSchoolForms(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute("schoolformItems", schoolformRepo.findAll());
		model.addAttribute("entityName", "schoolform");
		return "edit/schoolformList";
	}
	
	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSchoolForm(@PathVariable long id, Model model) {

		Set<SchoolForm> schoolformItems = new HashSet<>();
		Optional<SchoolForm> opt = schoolformRepo.findById(id);
		opt.ifPresent(schoolForm -> schoolformItems.add(schoolForm)); 
		
		model.addAttribute("schoolformItems", schoolformItems);
		return "edit/schoolformDetails"; 
	}

	//////////////////////////// DELETE ////////////////////////////

	@GetMapping("/delete/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "schoolform");
		return "form/confirmDelete";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSchoolForm(@PathVariable long id) {

		Optional<SchoolForm> opt = schoolformRepo.findById(id);
		opt.ifPresent(schoolForm -> {

			for (Student student : schoolForm.getStudents()) {
				schoolForm.removeStudent(student);
			}

			schoolformRepo.deleteById(id);

		});
		
		return "redirect:/schoolform/list";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {
		Optional<SchoolForm> opt = schoolformRepo.findById(id);
		opt.ifPresent(schoolForm -> {
			model.addAttribute(schoolForm);
			
		});

		return "form/schoolformForm";
	}

}
