package pl.ust.school.testpractice;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.ust.school.HomeController;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
// In this test, the full Spring application context is started, but without the server.
//We can narrow down the tests to just the web layer by using @WebMvcTest instead of @AutoConfigureMockMvc + @SpringBootTest
 

// wszystkie controllers są tworzone, jeśli tu nie zawęzimy ich 
 // np. @WebMvcTest(SchoolformController.class) W konsekwencji wymagane sa również collaborators/dependencies
// tych wszystkich kontrolerów. Jesli ich nie dodamy przez  @MockBean, to application will fail to start, eg
// No qualifying bean of type 'pl.ust.school.repository.SchoolformRepository' available

// with @WebMvcTest Spring Boot is only instantiating the web layer, not the whole context. In an application with multiple controllers 
//you can even ask for just one to be instantiated, using, for example @WebMvcTest(HomeController.class)
@WebMvcTest(HomeController.class) // will show mapping only for this controller
public class HttpRequestNoServerMockTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/helloworld"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Hello World")));
	}
	/*
	@MockBean // pure Mockito annotation
    private GreetingService service;
	
	@Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(service.greet()).thenReturn("Hello Mock"); // Mockito sets the expectations of service
        this.mockMvc.perform(get("/greeting"))
        	.andDo(print())
        	.andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello Mock")));
    }
*/
	
}
