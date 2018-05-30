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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.subject.SubjectController;
import pl.ust.school.subject.SubjectDto;
import pl.ust.school.subject.SubjectService;

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "subject/subjectForm";
	private static final String LIST_VIEW = "subject/subjectList";
	private static final String DETAILS_VIEW = "subject/subjectDetails";
	@SuppressWarnings("unused")
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";
	
	private static final String COLLECTION_OF_SUBJECTS_NAME = "subjectItems";

    private static final long TEST_SUBJECT_ID = 1L;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private SubjectService subjectService;

    private SubjectDto biology;
    
    @Before
    public void setup() {
    	this.biology = new SubjectDto();
    	this.biology.setId(TEST_SUBJECT_ID);
    	this.biology.setName("Biology");
    	
		given(this.subjectService.getSubjectDtoById(TEST_SUBJECT_ID)).willReturn(Optional.of(this.biology));

        
        System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see when each test starts/ends
    }

    @Test
    public void shouldShowSubjectFormWhenGetRequest() throws Exception {
        mockMvc.perform(get("/subject/save"))
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("subjectDto"))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }

    @Test
    public void shouldAddNewSubjectWhenNoErrors() throws Exception {
        mockMvc.perform( post("/subject/save")
        	   .param("name", "Astrophysics") )
        	   .andDo(print())
               .andExpect(status().is3xxRedirection()); 
    }
    
    @Test
    public void shouldFindErrorWhenNameIsEmptyString() throws Exception {
    	
        mockMvc.perform(post("/subject/save")
            .param("name", "")
        )
        	.andDo(print())
            .andExpect(status().isOk()) 
            .andExpect(model().attributeHasErrors("subjectDto"))
            .andExpect(model().attributeHasFieldErrors("subjectDto", "name"))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }
   
    @Test
    public void shouldRetrieveListOfSubjects() throws Exception {
    	
        given(this.subjectService.getAllSubjectDtos()).willReturn(Lists.newArrayList(this.biology, new SubjectDto()));
        mockMvc.perform(get("/subject/list"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(COLLECTION_OF_SUBJECTS_NAME))
            .andExpect(view().name(LIST_VIEW));
    }
   
    @Test
    public void shouldRetrieveSubjectByIdWhenExists() throws Exception {
        mockMvc.perform(get("/subject/view/{id}", TEST_SUBJECT_ID)
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(model().attributeExists(COLLECTION_OF_SUBJECTS_NAME))
        	//.andExpect(model().attribute("subjectItems", this.b)
            .andExpect(view().name(DETAILS_VIEW));
    }
  
    @Test
    public void shouldReturn404HttpWhenNotFound() throws Exception {
    	 given(this.subjectService.getSubjectDtoById(-1L)).willReturn((Optional.empty()));
        mockMvc.perform(get("/subject/view/{id}", -1)
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(model().attributeExists("notFound"))
            .andExpect(view().name(DETAILS_VIEW));
    }

     @Test
    public void shouldShowUpdateForm() throws Exception {
    	
        mockMvc.perform(get("/subject/update/{id}", TEST_SUBJECT_ID))
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("subjectDto"))
            .andExpect(model().attribute("subjectDto", this.biology))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }

    @Test
    public void shouldProcessUpdateWhenNoErrors() throws Exception {
        mockMvc.perform( post("/subject/update/{id}", TEST_SUBJECT_ID)
            .param("name", "Astrophysics")
        )
        	// .andExpect(model().attributeHasNoErrors("subject"))  No BindingResult for attribute: product WHY?!
        	// This exception is caused because the view returned by tested controller is a redirect: "redirect:....". 
        	// You could use it only for a view not being a redirect.
        	.andDo(print())
        	.andExpect(model().hasNoErrors())
        	.andExpect(status().is3xxRedirection())
            .andExpect( redirectedUrl("/subject/view/" + TEST_SUBJECT_ID));
    }
   
    @Test
    public void shouldReturnUpdateFormWhenErrors() throws Exception {
        mockMvc.perform( post("/subject/update/{id}", TEST_SUBJECT_ID)
            .param("name", "")
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(model().attributeHasErrors("subjectDto"))
        	.andExpect(model().attributeHasFieldErrors("subjectDto", "name"))
        	.andExpect(model().errorCount(1))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }
    
    
    @Ignore @Test
    public void shouldAskForConfirmationBeforeDeleting() throws Exception {
    	
    	mockMvc.perform(get("/subject/delete/{id}/confirm", TEST_SUBJECT_ID))
    	.andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(view().name(CONFIRM_DELETE_VIEW));
    }
    
    @Ignore @Test
    public void shouldDeleteSuccessfully() throws Exception {
    	
    	//then
    	mockMvc.perform(get("/subject/delete/{id}", TEST_SUBJECT_ID))
    	.andDo(print())
    	
    	//assert
    	.andExpect(status().is3xxRedirection())
    	.andExpect( redirectedUrl("/subject/list"));
    }

}
