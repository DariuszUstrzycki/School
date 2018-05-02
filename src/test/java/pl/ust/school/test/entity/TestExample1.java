package pl.ust.school.test.entity;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.entity.SchoolForm;
import pl.ust.school.repository.SchoolFormRepository;

@RunWith(SpringRunner.class) // SpringRunner is an alias for the SpringJUnit4ClassRunner.
@SpringBootTest // we are asking for the whole application context to be created. An alternative would be 
//to ask Spring Boot to create only the web layers of the context using the @WebMvcTest
@AutoConfigureMockMvc
@Transactional
public class TestExample1 {
	
	  /*
		MockMvc comes from Spring Test and allows you, via a set of convenient builder classes, to send 
		HTTP requests into the DispatcherServlet and make assertions about the result. 
		*/
	@Autowired
	private MockMvc mvc;
     
    @Resource
    private SchoolFormRepository schoolFormRepository;
     
    @SuppressWarnings("unchecked")
	@Test
    public void givenStudent_whenSave_thenGetOk() {
    	
    	SchoolForm schoolForm = new SchoolForm();
    	String formName = "1A";
    	schoolForm.setName(formName);
    	
    	schoolForm =  (SchoolForm) schoolFormRepository.save(schoolForm);
    	long newlyCreatedId = schoolForm.getId();
         
        Optional<SchoolForm> opt = (Optional<SchoolForm>) schoolFormRepository.findById(newlyCreatedId);
        SchoolForm schoolForm2 = opt.get();
        assertEquals(formName, schoolForm2.getName());
    }
}
