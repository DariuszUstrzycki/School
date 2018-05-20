package pl.ust.school.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.dto.StudentDto;
import pl.ust.school.entity.SchoolForm;
import pl.ust.school.service.SchoolFormService;
import pl.ust.school.service.StudentService;


// allows the Web App Context to be loaded. By default Spring will load the context into a Static variable so it only gets 
//initialized once per test run saving a lot of time. This his helpful if you create a base test class with the context 
//info and reuse it across your project.
@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/studentForm";
	private static final String LIST_VIEW = "detailsNLists/studentList";
	private static final String DETAILS_VIEW = "detailsNLists/studentDetails";
	@SuppressWarnings("unused")
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";

	private static final String COLLECTION_OF_STUDENTS_NAME = "studentItems";
	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "schoolformItems";

	private static final long TEST_STUDENT_ID = 1L;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	@MockBean
	private SchoolFormService schoolFormService;

	private StudentDto john;

	@Before
	public void setup() {
		john = new StudentDto();
		john.setId(TEST_STUDENT_ID);
		john.setAddress("Penny Lane 12, London, England");
		john.setBirthDate(LocalDate.of(2000, 1, 1));
		john.setEmail("john@gmail.com");
		john.setFirstName("John");
		john.setLastName("Brown");
		john.setPassword("123");
		john.setSchoolForm(new SchoolForm());
		john.setTelephone("1234567");
		
		given(this.studentService.getStudentById(TEST_STUDENT_ID)).willReturn( Optional.of(this.john));

		System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see
	}

	@Test
	public void shouldShowStudentFormWhenGetRequest() throws Exception {
		mockMvc.perform(get("/student/save")).andDo(print()) // !
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
	            .andExpect(model().attributeExists("studentDto"))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	@Test
	public void shouldAddNewStudent() throws Exception {
		mockMvc.perform(post("/student/save")
				.param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England")
				.param("email", "maria@gmail.com")
				.param("firstName", "Maria")
				.param("lastName", "Smith")
				.param("password", "000777"))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void shouldFindErrorsWhenInvalidValues() throws Exception {
		mockMvc.perform(post("/student/save")
				.param("firstName", "")
				.param("email", "<error>")
				.param("telephone", "<error>")
				.param("password", ""))

				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("studentDto"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "firstName"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("studentDto", "address")) // null
				.andExpect(model().attributeHasFieldErrors("studentDto", "email"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "telephone"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "password"))
				.andExpect(model().attributeErrorCount("studentDto", 6))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	@Test
	public void shouldRetrieveListOfStudents() throws Exception {
		given(this.studentService.getAllStudents()).willReturn(Lists.newArrayList(john, new StudentDto()));

		mockMvc.perform(get("/student/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
				.andExpect(model().attributeExists(COLLECTION_OF_STUDENTS_NAME))
				.andExpect(view().name(LIST_VIEW));
	}

	@Test
	public void shouldRetrieveStudentByIdWhenExists() throws Exception {

		mockMvc.perform(get("/student/view/{id}", TEST_STUDENT_ID))
				
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_STUDENTS_NAME))
				// how to check that the attribute is a collection?! and its size?
				.andExpect(view().name(DETAILS_VIEW));
	}

	@Test
	public void shouldReturn404HttpWhenNotFound() throws Exception {
		given(this.studentService.getStudentById(-1L)).willReturn((Optional.empty()));

		mockMvc.perform(get("/student/view/{id}", -1))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(model().attributeExists("notFound")).andExpect(view().name(DETAILS_VIEW));
	}

	@Test
	public void shouldShowUpdateForm() throws Exception {

		mockMvc.perform(get("/student/update/{id}", TEST_STUDENT_ID))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
				.andExpect(model().attributeExists("studentDto")).andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	@Test
	public void shouldProcessUpdateWhenNoErrors() throws Exception {
		mockMvc.perform(post("/student/update/{id}", TEST_STUDENT_ID).param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England").param("email", "maria@gmail.com")
				.param("firstName", "Maria").param("lastName", "Smith").param("password", "000777"))
				// .andExpect(model().attributeHasNoErrors("student")) No BindingResult for
				// attribute: product WHY?!
				// This exception is caused because the view returned by tested controller is a
				// redirect: "redirect:....".
				// You could use it only for a view not being a redirect.
				.andDo(print())
				.andExpect(model().hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect( redirectedUrl("/student/view/" + TEST_STUDENT_ID));
	}

	@Test
	public void shouldReturnUpdateFormWhenErrors() throws Exception {
		mockMvc.perform(post("/student/update/{id}", TEST_STUDENT_ID)
				.param("firstName", "")
				.param("email", "<error>")
				.param("telephone", "<error>")
				.param("password", ""))

				.andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
				.andExpect(model().attributeHasErrors("studentDto"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "firstName"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("studentDto", "address")) // null
				.andExpect(model().attributeHasFieldErrors("studentDto", "email"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "telephone"))
				.andExpect(model().attributeHasFieldErrors("studentDto", "password"))
				.andExpect(model().attributeErrorCount("studentDto", 6))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}
	
	 @Test
	    public void shouldAskForConfirmationBeforeDeleting() throws Exception {
	    	
	    	mockMvc.perform(get("/student/delete/{id}/confirm", TEST_STUDENT_ID))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(view().name(CONFIRM_DELETE_VIEW));
	    }
	    
	    @Test
	    public void shouldDeleteSuccessfully() throws Exception {
	    	
	    	//then
	    	mockMvc.perform(get("/student/delete/{id}", TEST_STUDENT_ID))
	    	.andDo(print())
	    	
	    	//assert
	    	.andExpect(status().is3xxRedirection())
	    	.andExpect( redirectedUrl("/student/list"));
	    }

}
