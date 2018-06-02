package pl.ust.school;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping
	public String goToIndex() {
		return "index";
	}  
	
	@RequestMapping("/locale")
	@ResponseBody
    public String home(Locale locale) {
        return locale.toString();
    }
	
	@RequestMapping("/helloworld")
    public @ResponseBody String helloworld() {
        return "Hello World";
    }

}
