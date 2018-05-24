package pl.ust.school.controller;

import java.util.HashSet;
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

import pl.ust.school.dto.SchoolformDto;
import pl.ust.school.service.SchoolformService;
import pl.ust.school.service.StudentService;

@Controller
@RequestMapping("schoolform") 
public class SchoolformController {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/schoolformForm";
	private static final String LIST_VIEW = "detailsNLists/schoolformList";
	private static final String DETAILS_VIEW = "detailsNLists/schoolformDetails";
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "schoolformItems";
	private static final String COLLECTION_OF_STUDENTS_NAME = "studentItems";
	private static final String ENTITY_NAME = "entityName";
	private static final String ENTITY_NAME_VALUE = "schoolform";
	
	@Autowired
	private SchoolformService schoolformService;
	
	@Autowired
	private StudentService studentService;
	
	@InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
	
	////////////////////////////SAVE ////////////////////////////
	
	@GetMapping("/save")
	public String showForm(SchoolformDto schoolformDto, Model model) {
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE); // endpoint should be read some config file
		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	
	@PostMapping("/save")
	public String saveSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result, Model model) {
		
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE); // endpoint should be read some config file
		
		if(result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println(error);
			}
			return CREATE_OR_UPDATE_FORM_VIEW; 
		}
		
		long id = this.schoolformService.createSchoolform(schoolformDto);	
		return "redirect:/schoolform/view/" + id; 
	}
		
	////////////////////////////LIST ////////////////////////////
	
	@RequestMapping("/list")
	public String listSchoolforms(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, this.schoolformService.getAllSchoolforms());
		model.addAttribute(ENTITY_NAME, ENTITY_NAME_VALUE);
		return LIST_VIEW;
	}
	
	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("view/{id}")
	public String viewSchoolform(@PathVariable long id, Model model) {

		
		Optional<SchoolformDto> opt =  this.schoolformService.getSchoolformById(id);
		
		if(opt.isPresent()) {
			Set<SchoolformDto> schoolformItems = new HashSet<>();
			SchoolformDto schoolformDto = opt.get();
			schoolformItems.add(schoolformDto);
			model.addAttribute("schoolformName", schoolformDto.getName());
			model.addAttribute(COLLECTION_OF_SCHOOLFORMS_NAME, schoolformItems);
			model.addAttribute(COLLECTION_OF_STUDENTS_NAME, this.studentService.getStudentBySchoolform_Id(id));
			// 
		} else {
			throw new RecordNotFoundException("No school form with id " + id + " has been found.");
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
	public String deleteSchoolform(@PathVariable long id) {
		
		 this.schoolformService.deleteSchoolform(id);
		
		
		return "redirect:/schoolform/list";
	}
	

	//////////////////////////// UPDATE ////////////////////////////
	@GetMapping("/update/{id}")
	public String showForm(@PathVariable long id, Model model) {
		Optional<SchoolformDto> subjectDto = this.schoolformService.getSchoolformById(id);
		
		if(subjectDto.isPresent()) {
			model.addAttribute(subjectDto.get());
		}

		return CREATE_OR_UPDATE_FORM_VIEW;
	}
	

	@PostMapping("/update/{id}")
	public String updateSchoolform(@Valid SchoolformDto schoolformDto, BindingResult result, @PathVariable long id, Model model) {
		
		if (result.hasErrors()) {
	         return CREATE_OR_UPDATE_FORM_VIEW;
	     } else {
	    	 schoolformDto.setId(id);
	         this.schoolformService.createSchoolform(schoolformDto);
	         return "redirect:/schoolform/view/" + id;
	     }
		
	}

	////////////////////// exception handling ////////////////////////////////////

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private String recordNotFoundHandler(RecordNotFoundException ex, Model model) {
		model.addAttribute("notFound", ex.getMessage());
		return DETAILS_VIEW;
	}

}
