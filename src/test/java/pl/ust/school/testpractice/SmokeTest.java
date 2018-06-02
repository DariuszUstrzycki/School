package pl.ust.school.testpractice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.school.HomeController;

@RunWith(SpringRunner.class) // bez tego nie zrobi Autowired
@SpringBootTest // tells Spring Boot to go and look for a main configuration class (one with 
//@SpringBootApplication for instance), and use that to start a Spring application context. 
// default: loads the whole context
public class SmokeTest { // sanity check
	
	@Autowired
    private HomeController homeController;

    @Test
    public void contexLoads() throws Exception {
        assertThat(homeController).isNotNull(); // https://joel-costigliola.github.io/assertj/  Fluent assertions for java
    }
    
    /*
    A nice feature of the Spring Test support is that the application context is cached in between tests, so if you have multiple 
    methods in a test case, or multiple test cases with the same configuration, they only incur the cost of starting 
    the application once. You can control the cache using the @DirtiesContext annotation.*/
    
    

}
