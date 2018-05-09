package pl.ust.school.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

import pl.ust.school.entity.Subject;
import pl.ust.school.repository.SubjectRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {
	
	private static final String CREATE_OR_UPDATE_FORM_VIEW = "forms/subjectForm";
	private static final String LIST_VIEW = "detailsNLists/subjectList";
	private static final String DETAILS_VIEW = "detailsNLists/subjectDetails";
	@SuppressWarnings("unused")
	private static final String CONFIRM_DELETE_VIEW = "forms/confirmDelete";


    private static final long TEST_SUBJECT_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectRepository subjectRepo;

    private Subject biology;
    
    @Before
    public void setup() {
    	biology = new Subject();
    	biology.setId(TEST_SUBJECT_ID);
    	biology.setName("Biology");
        given(this.subjectRepo.findById(TEST_SUBJECT_ID)).willReturn(Optional.of(biology));
        
        System.err.println("----------@Before setup()-----------------"); // useful when debugging as it's easy to see when each test starts/ends
    }

    @Test
    public void shouldShowSubjectFormWhenGetRequest() throws Exception {
        mockMvc.perform(get("/subject/save"))
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("subject"))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }

    @Test
    public void shouldProcessNewSubjectWhenPostRequest() throws Exception {
        mockMvc.perform( post("/subject/save")
        	   .param("name", "Astrophysics") )
        		.andDo(print())
               .andExpect(status().is3xxRedirection()); // 307/8 Temporary/Permament Redirect
    }
    
    @Test
    public void shouldFindErrorWhenNameIsEmptyString() throws Exception {
    	
        mockMvc.perform(post("/subject/save")
            .param("name", "")
        )
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("subject"))
            .andExpect(model().attributeHasFieldErrors("subject", "name"))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }
   
    @Test
    public void shouldRetrieveListOfSubjects() throws Exception {
    	
        given(this.subjectRepo.findAll()).willReturn(Lists.newArrayList(biology, new Subject()));
        mockMvc.perform(get("/subject/list"))
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("subjectItems"))
            .andExpect(view().name(LIST_VIEW));
    }
   
    @Test
    public void shouldRetrieveSubjectByIdWhenExists() throws Exception {
        given(this.subjectRepo.findById(biology.getId())).willReturn((Optional.of(biology)));
        mockMvc.perform(get("/subject/view/{id}", TEST_SUBJECT_ID)
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(model().attributeExists("subjectItems"))
        	// how to check that the attribute is a collection?! and its size?
            .andExpect(view().name(DETAILS_VIEW));
    }
  
    @Test
    public void shouldDisplayNotFoundMessageWhenNoSubjectFound() throws Exception {
    	 given(this.subjectRepo.findById(-1L)).willReturn((Optional.empty()));
        mockMvc.perform(get("/subject/view/{id}", -1)
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(model().attributeExists("notFound"))
            .andExpect(view().name(DETAILS_VIEW));
    }

   // @Test
    public void shouldShowUpdateForm() throws Exception {
    	given(this.subjectRepo.findById(biology.getId())).willReturn((Optional.of(biology)));
    	
        mockMvc.perform(get("/subject/update/{id}", TEST_SUBJECT_ID))
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("subject"))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }

    @Test
    public void shouldProcessUpdateWhenNoErrors() throws Exception {
        mockMvc.perform( post("/subject/update/{id}", TEST_SUBJECT_ID)
            .param("name", "Maths")
        )
        	// .andExpect(model().attributeHasNoErrors("subject"))  No BindingResult for attribute: product WHY?!
        	// This exception is caused because the view returned by tested controller is a redirect: "redirect:....". 
        	// You could use it only for a view not being a redirect.
        	.andDo(print())
        	.andExpect(model().hasNoErrors())
        	.andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/subject/view/" + TEST_SUBJECT_ID));
    }
   
    //@Test
    public void shouldReturnUpdateFormWhenErrors() throws Exception {
        mockMvc.perform( post("/subject/update/{id}", TEST_SUBJECT_ID)
        		.contentType(MediaType.TEXT_HTML)
        		
            .param("name", "")
        )
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(model().attributeHasErrors("subject"))
        	.andExpect(model().attributeHasFieldErrors("subject", "name"))
        	.andExpect(model().errorCount(1))
            .andExpect(view().name(CREATE_OR_UPDATE_FORM_VIEW));
    }
    
    
    /// MIssing tests for delete operations

}
