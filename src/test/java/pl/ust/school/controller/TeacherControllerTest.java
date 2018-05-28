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
import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.dto.SubjectDto;
import pl.ust.school.dto.TeacherDto;
import pl.ust.school.service.SubjectService;
import pl.ust.school.service.TeacherService;

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
	private TeacherService teacherService;
	
	@MockBean
	private SubjectService subjectService;

	private TeacherDto john;
	
	@Before
	public void setup() {
		john = new TeacherDto();
		john.setId(TEST_TEACHER_ID);
		john.setAddress("Penny Lane 12, London, England");
		john.setBirthDate(LocalDate.of(2000, 1, 1));
		john.setEmail("john@gmail.com");
		john.setFirstName("John");
		john.setLastName("Brown");
		john.setPassword("123");
		john.setTelephone("1234567");
		
		given(this.teacherService.getTeacherDtoById(TEST_TEACHER_ID)).willReturn(Optional.of(this.john));

		System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see
																			// when each test starts/ends
	}
	
	@Test
	public void shouldShowTeacherFormWhenGetRequest() throws Exception {
		mockMvc.perform(get("/teacher/save"))
		.andDo(print())
		.andExpect(status().isOk())
        .andExpect(model().attributeExists("teacherDto")) 
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

	@Ignore @Test
	public void shouldFindErrorsWhenInvalidValues() throws Exception {

		mockMvc.perform(post("/teacher/save")
				.param("firstName", "")
				.param("email", "123")
				.param("telephone", "abc")
				.param("password", ""))
				
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("teacherDto"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "firstName"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("teacherDto", "address")) // null
				.andExpect(model().attributeHasFieldErrors("teacherDto", "email"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "telephone"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "password"))
				.andExpect(model().attributeErrorCount("teacherDto", 6))
				.andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	}
	
	@Test
	public void shouldRetrieveListOfTeachers() throws Exception {

		given(this.teacherService.getAllTeacherDtos()).willReturn(Lists.newArrayList(john, new TeacherDto()));
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
				.andExpect(view().name(DETAILS_VIEW));
	}

	@Test
	public void shouldReturn404HttpWhenNotFound() throws Exception {
		given(this.teacherService.getTeacherDtoById(-1L)).willReturn((Optional.empty()));
		
		mockMvc.perform(get("/teacher/view/{id}", -1))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(model().attributeExists("notFound")).andExpect(view().name(DETAILS_VIEW));
	}

	@Test
	public void shouldShowUpdateForm() throws Exception {
		
		given(this.teacherService.getSubjectsNotTaughtByTeacher( new TeacherDto(), new ArrayList<SubjectDto>()))
													.willReturn(Lists.newArrayList( new SubjectDto()));

		mockMvc.perform(get("/teacher/update/{id}", TEST_TEACHER_ID))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("teacherDto"))
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

	@Ignore @Test
	public void shouldReturnUpdateFormWhenErrors() throws Exception {
		mockMvc.perform(post("/teacher/update/{id}", TEST_TEACHER_ID)
				.param("firstName", "")
				.param("email", "<error>")
				.param("telephone", "<error>")
				.param("password", ""))
		
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("teacherDto"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "firstName"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "lastName")) // null
				.andExpect(model().attributeHasFieldErrors("teacherDto", "address")) // null
				.andExpect(model().attributeHasFieldErrors("teacherDto", "email"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "telephone"))
				.andExpect(model().attributeHasFieldErrors("teacherDto", "password"))
				.andExpect(model().attributeErrorCount("teacherDto", 6))
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
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/teacher/list"));
	}
	
	@Test  
	public void shouldProcessSubjectRemovalSuccessfully() throws Exception {
		
		mockMvc.perform(get("/teacher/{teacherId}/subject/{subjectId}/remove"  , TEST_TEACHER_ID, 1L))
		.andDo(print())

		// assert
		.andExpect(status().is3xxRedirection())
		.andExpect( redirectedUrl("/teacher/update/" + TEST_TEACHER_ID));
	}
	
	@Test
	public void shouldProcessSubjectAdditionSuccessfully() throws Exception {
		
		mockMvc.perform(get("/teacher/{teacherId}/subject/{subjectId}/add", TEST_TEACHER_ID, 1L))
		.andDo(print())
		
		// assert
		.andExpect(status().is3xxRedirection())
		.andExpect( redirectedUrl("/teacher/update/" + TEST_TEACHER_ID));
		
	}

}
