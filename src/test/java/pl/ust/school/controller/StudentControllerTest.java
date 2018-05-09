package pl.ust.school.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

import pl.ust.school.entity.SchoolForm;
import pl.ust.school.entity.Student;
import pl.ust.school.repository.SchoolFormRepository;
import pl.ust.school.repository.StudentRepository;

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
	private StudentRepository studentRepo;

	@MockBean
	private SchoolFormRepository schoolFormRepo;

	private Student john;

	@Before
	public void setup() {
		john = new Student();
		john.setId(TEST_STUDENT_ID);
		john.setAddress("Penny Lane 12, London, England");
		john.setBirthDate(LocalDate.of(2000, 1, 1));
		john.setEmail("john@gmail.com");
		john.setFirstName("John");
		john.setLastName("Brown");
		john.setPassword("123");
		john.setSchoolForm(new SchoolForm());
		john.setTelephone("1234567");
		given(this.studentRepo.findById(TEST_STUDENT_ID)).willReturn(Optional.of(john));

		System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see
																			// when each test starts/ends
	}

	@Test
	public void shouldShowStudentFormWhenGetRequest() throws Exception {
		mockMvc.perform(get("/student/save")).andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
				.andExpect(model().attributeExists("student")).andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	// @Test
	public void shouldAddNewStudent() throws Exception {
		mockMvc.perform(post("/student/save").param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England").param("email", "maria@gmail.com")
				.param("firstName", "Maria").param("lastName", "Smith").param("password", "000777")).andDo(print())
				.andExpect(status().is3xxRedirection());
	}

	// @Test
	public void shouldFindErrorsWhenInvalidValues() throws Exception {

		mockMvc.perform(post("/student/save")
				.param("firstName", "")
				.param("email", "123")
				.param("telephone", "abc")
				.param("password", ""))
				
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("student"))
				.andExpect(model().attributeHasFieldErrors("student", "firstName"))
				.andExpect(model().attributeHasFieldErrors("student", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("student", "address")) // null
				.andExpect(model().attributeHasFieldErrors("student", "email"))
				.andExpect(model().attributeHasFieldErrors("student", "telephone"))
				.andExpect(model().attributeHasFieldErrors("student", "password"))
				.andExpect(model().attributeErrorCount("student", 6))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	// @Test
	public void shouldRetrieveListOfStudents() throws Exception {

		given(this.studentRepo.findAll()).willReturn(Lists.newArrayList(john, new Student()));
		mockMvc.perform(get("/student/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
				.andExpect(model().attributeExists(COLLECTION_OF_STUDENTS_NAME)).andExpect(view().name(LIST_VIEW));
	}

	// @Test
	public void shouldRetrieveStudentByIdWhenExists() throws Exception {
		given(this.studentRepo.findById(john.getId())).willReturn((Optional.of(john)));
		mockMvc.perform(get("/student/view/{id}", TEST_STUDENT_ID)).andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_STUDENTS_NAME))
				// how to check that the attribute is a collection?! and its size?
				.andExpect(view().name(DETAILS_VIEW));
	}

	@Test
	public void shouldDisplayNotFoundMessageWhenNoStudentFound() throws Exception {
		given(this.studentRepo.findById(-1L)).willReturn((Optional.empty()));
		mockMvc.perform(get("/student/view/{id}", -1)).andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeExists("notFound")).andExpect(view().name(DETAILS_VIEW));
	}

	// @Test
	public void shouldShowUpdateForm() throws Exception {
		given(this.studentRepo.findById(john.getId())).willReturn((Optional.of(john)));

		mockMvc.perform(get("/student/update/{id}", TEST_STUDENT_ID))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
				.andExpect(model().attributeExists("student"))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	//@Test
	public void shouldProcessUpdateWhenNoErrors() throws Exception {
		mockMvc.perform(post("/student/update/{id}", TEST_STUDENT_ID).param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England").param("email", "maria@gmail.com")
				.param("firstName", "Maria").param("lastName", "Smith").param("password", "000777"))
				// .andExpect(model().attributeHasNoErrors("student")) No BindingResult for
				// attribute: product WHY?!
				// This exception is caused because the view returned by tested controller is a
				// redirect: "redirect:....".
				// You could use it only for a view not being a redirect.
				.andDo(print()).andExpect(model().hasNoErrors()).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/student/view/" + TEST_STUDENT_ID));
	}

	@Test
	public void shouldReturnUpdateFormWhenErrors() throws Exception {
		mockMvc.perform(post("/student/update/{id}", TEST_STUDENT_ID)
				.param("firstName", "")
				.param("email", "123")
				.param("telephone", "abc")
				.param("password", ""))
		
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
				.andExpect(model().attributeHasErrors("student"))
				.andExpect(model().attributeHasFieldErrors("student", "firstName"))
				.andExpect(model().attributeHasFieldErrors("student", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("student", "address")) // null
				.andExpect(model().attributeHasFieldErrors("student", "email"))
				.andExpect(model().attributeHasFieldErrors("student", "telephone"))
				.andExpect(model().attributeHasFieldErrors("student", "password"))
				.andExpect(model().attributeErrorCount("student", 6))
			    .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

}
