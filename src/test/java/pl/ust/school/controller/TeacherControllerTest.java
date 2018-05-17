package pl.ust.school.controller;

import static org.assertj.core.api.Assertions.*;
import org.assertj.core.util.*;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import pl.ust.school.entity.Teacher;
import pl.ust.school.repository.TeacherRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/teacherForm";
	private static final String LIST_VIEW = "detailsNLists/teacherList";
	private static final String DETAILS_VIEW = "detailsNLists/teacherDetails";
	@SuppressWarnings("unused")
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	private static final String COLLECTION_OF_TEACHERS_NAME = "teacherItems";
	
	private static final long TEST_TEACHER_ID = 1L;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TeacherRepository teacherRepo;

	private Teacher john;
	
	@Before
	public void setup() {
		john = new Teacher();
		john.setId(TEST_TEACHER_ID);
		john.setAddress("Penny Lane 12, London, England");
		john.setBirthDate(LocalDate.of(2000, 1, 1));
		john.setEmail("john@gmail.com");
		john.setFirstName("John");
		john.setLastName("Brown");
		john.setPassword("123");
		john.setTelephone("1234567");
		
		 given(this.teacherRepo.findById(TEST_TEACHER_ID)).willReturn(Optional.of(this.john));

		System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see
																			// when each test starts/ends
	}
	
	@Test
	public void shouldShowTeacherFormWhenGetRequest() throws Exception {
		mockMvc.perform(get("/teacher/save"))
		.andDo(print())
		.andExpect(status().isOk())
        .andExpect(model().attributeExists("teacher")) 
        // .andExpect(model().attribute("teacher",  TeacherDTO.class))
		.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	@Test
	public void shouldAddNewTeacher() throws Exception {
		mockMvc.perform(post("/teacher/save")
				.param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England")
				.param("email", "maria@gmail.com")
				.param("firstName", "Maria")
				.param("lastName", "Smith").param("password", "000777"))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void shouldFindErrorsWhenInvalidValues() throws Exception {

		mockMvc.perform(post("/teacher/save")
				.param("firstName", "")
				.param("email", "123")
				.param("telephone", "abc")
				.param("password", ""))
				
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("teacher"))
				.andExpect(model().attributeHasFieldErrors("teacher", "firstName"))
				.andExpect(model().attributeHasFieldErrors("teacher", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("teacher", "address")) // null
				.andExpect(model().attributeHasFieldErrors("teacher", "email"))
				.andExpect(model().attributeHasFieldErrors("teacher", "telephone"))
				.andExpect(model().attributeHasFieldErrors("teacher", "password"))
				.andExpect(model().attributeErrorCount("teacher", 6))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}
	
	@Test
	public void shouldRetrieveListOfTeachers() throws Exception {

		given(this.teacherRepo.findAll()).willReturn(Lists.newArrayList(john, new Teacher()));
		mockMvc.perform(get("/teacher/list"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_TEACHERS_NAME))
				.andExpect(view().name(LIST_VIEW));
	}

	@Test
	public void shouldRetrieveTeacherByIdWhenExists() throws Exception {

		mockMvc.perform(get("/teacher/view/{id}", TEST_TEACHER_ID))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(COLLECTION_OF_TEACHERS_NAME))
				// how to check that the attribute is a collection?! and its size?
				.andExpect(view().name(DETAILS_VIEW));
	}

	@Test
	public void shouldReturn404HttpWhenNotFound() throws Exception {
		given(this.teacherRepo.findById(-1L)).willReturn((Optional.empty()));
		
		mockMvc.perform(get("/teacher/view/{id}", -1))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(model().attributeExists("notFound")).andExpect(view().name(DETAILS_VIEW));
	}

	@Test
	public void shouldShowUpdateForm() throws Exception {

		mockMvc.perform(get("/teacher/update/{id}", TEST_TEACHER_ID))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("teacher"))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	@Test
	public void shouldProcessUpdateWhenNoErrors() throws Exception {
		mockMvc.perform(post("/teacher/update/{id}", TEST_TEACHER_ID).param("telephone", "1111111111")
				.param("address", "Penny Lane 12, London, England").param("email", "maria@gmail.com")
				.param("firstName", "Maria").param("lastName", "Smith").param("password", "000777"))
				// .andExpect(model().attributeHasNoErrors("teacher")) No BindingResult for
				// attribute: product WHY?!
				// This exception is caused because the view returned by tested controller is a
				// redirect: "redirect:....".
				// You could use it only for a view not being a redirect.
				.andDo(print())
				.andExpect(model()
				.hasNoErrors())
				.andExpect(status().is3xxRedirection())
				.andExpect( redirectedUrl("/teacher/view/" + TEST_TEACHER_ID));
	}

	@Test
	public void shouldReturnUpdateFormWhenErrors() throws Exception {
		mockMvc.perform(post("/teacher/update/{id}", TEST_TEACHER_ID)
				.param("firstName", "")
				.param("email", "<error>")
				.param("telephone", "<error>")
				.param("password", ""))
		
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("teacher"))
				.andExpect(model().attributeHasFieldErrors("teacher", "firstName"))
				.andExpect(model().attributeHasFieldErrors("teacher", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("teacher", "address")) // null
				.andExpect(model().attributeHasFieldErrors("teacher", "email"))
				.andExpect(model().attributeHasFieldErrors("teacher", "telephone"))
				.andExpect(model().attributeHasFieldErrors("teacher", "password"))
				.andExpect(model().attributeErrorCount("teacher", 6))
			    .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}

	@Test
	public void shouldAskForConfirmationBeforeDeleting() throws Exception {

		mockMvc.perform(get("/teacher/delete/{id}/confirm", TEST_TEACHER_ID))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name(CONFIRM_DELETE_VIEW));
	}

	@Test
	public void shouldDeleteSuccessfully() throws Exception {

		// then
		mockMvc.perform(get("/teacher/delete/{id}", TEST_TEACHER_ID))
				.andDo(print())

				// assert
				.andExpect(status()
				.is3xxRedirection())
				.andExpect(redirectedUrl("/teacher/list"));
	}

}
