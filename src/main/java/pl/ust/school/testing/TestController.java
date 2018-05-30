package pl.ust.school.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	TestEntityRepository repo;

	@PostMapping("/test/save")
	public void saveTestEntity(@ModelAttribute TestEntity testEntity) {
		 repo.save(testEntity);
	}

	@GetMapping("/test/view/{id}")
	public TestEntity saveTestEntity(@PathVariable Long id) {
		return repo.findById(id).get();
	}
}
