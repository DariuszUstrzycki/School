package pl.ust.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.ust.school.entity.TestEntity;
import pl.ust.school.repository.TestEntityRepository;

@RestController
public class TestController {

	@Autowired
	TestEntityRepository repo;

	@PostMapping("/test/save")
	public void saveTestEntity(@ModelAttribute TestEntity testEntity) {
		TestEntity saved =  (TestEntity) repo.save(testEntity);
	}

	@GetMapping("/test/view/{id}")
	public TestEntity saveTestEntity(@PathVariable Long id) {
		return (TestEntity) repo.findById(id).get();
	}
}
