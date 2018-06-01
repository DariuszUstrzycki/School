package pl.ust.school.testpractice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pl.ust.school.student.StudentControllerTest;
import pl.ust.school.student.StudentJPATest;
import pl.ust.school.student.StudentRepositoryTest;

@RunWith(Suite.class)
@SuiteClasses({ HttpRequestWithServerTest.class, StudentJPATest.class, StudentRepositoryTest.class, StudentControllerTest.class})
public class FourSanityTests {

}
