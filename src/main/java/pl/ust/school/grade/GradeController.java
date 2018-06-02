package pl.ust.school.grade;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.ust.school.student.StudentDto;
import pl.ust.school.student.StudentService;
import pl.ust.school.tss.TSSDto;
import pl.ust.school.tss.TSSService;

@Controller
@RequestMapping("grade")
public class GradeController {
	
	private static final String VIEW_TSS_LIST = "grade/tssList";
	private static final String VIEW_STUDENT_LIST = "grade/studentList";

	private static final String NAME_COLLECTION_OF_TSSES = "tssItems";
	private static final String NAME_COLLECTION_OF_STUDENTS = "studentItems";
	
	@Autowired
	TSSService tSSService;
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/tssList")
	public String showTTSes(Model model) {
		model.addAttribute(NAME_COLLECTION_OF_TSSES, this.tSSService.getAllTSSDtos());
		return VIEW_TSS_LIST;
	}

	@GetMapping("/showGrades/tSS/{tssId}") 
	public String showStudentsInTSS(@PathVariable long tssId, Model model) {
		
		TSSDto tssDto = tSSService.getTSSDto(tssId);
		tssDto = this.studentService.filterGradesWithSubject(tssDto);
		
		model.addAttribute("tssDto",  tssDto);
		model.addAttribute(NAME_COLLECTION_OF_STUDENTS, tssDto.getSchoolform().getStudents());
		return VIEW_STUDENT_LIST;
	}
	
	
	

}
