package pl.ust.school.app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pl.ust.school.controller.StudentControllerTest;
import pl.ust.school.repository.StudentJPATest;
import pl.ust.school.repository.StudentRepositoryTest;

@RunWith(Suite.class)
@SuiteClasses({ HttpRequestWithServerTest.class, StudentJPATest.class, StudentRepositoryTest.class, StudentControllerTest.class})
public class FourSanityTests {

}
