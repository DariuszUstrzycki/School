package pl.ust.school.controller;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.entity.SchoolForm;
import pl.ust.school.repository.SchoolFormRepository;
import pl.ust.school.repository.StudentRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(SchoolFormController.class)
public class SchoolFormControllerTest {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/schoolformForm";
	private static final String LIST_VIEW = "detailsNLists/schoolformList";
	private static final String DETAILS_VIEW = "detailsNLists/schoolformDetails";
	@SuppressWarnings("unused")
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	private static final String COLLECTION_OF_SCHOOLFORMS_NAME = "schoolformItems";
	private static final String COLLECTION_OF_STUDENTS_NAME = "studentItems";
	
	 private static final long TEST_SCHOOLFORM_ID = 1L;

	    @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private SchoolFormRepository schoolFormRepo;
	    
	    @MockBean
		private StudentRepository studentRepo;

	    private SchoolForm schoolform1A;
	    
	    @Before
	    public void setup() {
	    	schoolform1A = new SchoolForm();
	    	schoolform1A.setId(TEST_SCHOOLFORM_ID);
	    	schoolform1A.setName("1A");
	    	
	    	given(this.schoolFormRepo.findById(TEST_SCHOOLFORM_ID)).willReturn(Optional.of(this.schoolform1A));
	        
	        System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see when each test starts/ends
	    }
	    
	    @Test
	    public void shouldShowSchoolFormWhenGetRequest() throws Exception {
	        mockMvc.perform(get("/schoolform/save"))
	        	.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("schoolForm"))
	            // // .andExpect(model().attribute("teacher",  SchoolFormDTO.class))
	            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	    }

	    @Test
	    public void shouldAddNewSchoolform() throws Exception {
	        mockMvc.perform( post("/schoolform/save")
	        	   .param("name", "1A") )
	        		.andDo(print())
	               .andExpect(status().is3xxRedirection()); 
	    }
	    
	    @Test
	    public void shouldFindErrorWhenNameIsEmptyString() throws Exception {
	    	
	        mockMvc.perform(post("/schoolform/save")
	            .param("name", "")
	        )
	        	.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(model().attributeHasErrors("schoolForm"))
	            .andExpect(model().attributeHasFieldErrors("schoolForm", "name"))
	            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	    }
	   
	    @Test
	    public void shouldRetrieveListOfSchooForms() throws Exception {
	    	
	        given(this.schoolFormRepo.findAll()).willReturn(Lists.newArrayList(schoolform1A, new SchoolForm()));
	        mockMvc.perform(get("/schoolform/list"))
	        	.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
	            .andExpect(view().name(LIST_VIEW));
	    }
	    
	    @Test
	    public void shouldRetrieveSchooFormByIdWhenExists() throws Exception {
	        mockMvc.perform(get("/schoolform/view/{id}", TEST_SCHOOLFORM_ID)
	        )
	        	.andDo(print())
	        	.andExpect(status().isOk())
	        	.andExpect(model().attributeExists(COLLECTION_OF_SCHOOLFORMS_NAME))
	        	.andExpect(model().attributeExists(COLLECTION_OF_STUDENTS_NAME))
	        	// how to check that the attribute is a collection?! and its size?
	            .andExpect(view().name(DETAILS_VIEW));
	    }
	  
	    @Test
	    public void shouldReturn404HttpWhenNotFound() throws Exception {
	    	 given(this.schoolFormRepo.findById(-1L)).willReturn((Optional.empty()));
	        mockMvc.perform(get("/schoolform/view/{id}", -1)
	        )
	        	.andDo(print())
	        	.andExpect(status().isNotFound())
	        	.andExpect(model().attributeExists("notFound"))
	            .andExpect(view().name(DETAILS_VIEW));
	    }
	    
	    
	    @Test
	    public void shouldShowUpdateForm() throws Exception {
	    	
	        mockMvc.perform(get("/schoolform/update/{id}", TEST_SCHOOLFORM_ID))
	        	.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(model().attributeExists("schoolForm"))
	            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	    }

	    @Test
	    public void shouldProcessUpdateWhenNoErrors() throws Exception {
	        mockMvc.perform( post("/schoolform/update/{id}", TEST_SCHOOLFORM_ID)
	            .param("name", "2B")
	        )
	        	// .andExpect(model().attributeHasNoErrors("schooForm"))  No BindingResult for attribute: product WHY?!
	        	// This exception is caused because the view returned by tested controller is a redirect: "redirect:....". 
	        	// You could use it only for a view not being a redirect.
	        	.andDo(print())
	        	.andExpect(model().hasNoErrors())
	        	.andExpect(status().is3xxRedirection())
	        	 .andExpect( redirectedUrl("/schoolform/view/" + TEST_SCHOOLFORM_ID));
	    }
	    
	   @Test
	    public void shouldReturnUpdateFormWhenErrors() throws Exception {
	        mockMvc.perform( post("/schoolform/update/{id}", TEST_SCHOOLFORM_ID)
	        		.contentType(MediaType.TEXT_HTML)
	        		
	            .param("name", "")
	        )
	        	.andDo(print())
	        	.andExpect(status().isOk())
	        	.andExpect(model().attributeHasErrors("schoolForm"))
	        	.andExpect(model().attributeHasFieldErrors("schoolForm", "name"))
	        	.andExpect(model().errorCount(1))
	            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
	    }
	    
	    
	    /// MIssing tests for delete operations
	   

}
