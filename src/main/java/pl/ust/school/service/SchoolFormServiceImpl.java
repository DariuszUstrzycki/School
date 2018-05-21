package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.SchoolFormDto;
import pl.ust.school.entity.SchoolForm;
import pl.ust.school.mapper.SchoolFormMapper;
import pl.ust.school.repository.SchoolFormRepository;

@Service
public class SchoolFormServiceImpl implements SchoolFormService{

	@Autowired
	private SchoolFormRepository repo;

	@Autowired
	private SchoolFormMapper mapper;

	public long  createSchoolForm(SchoolFormDto schoolFormDto) {
		SchoolForm schoolForm = this.mapper.fromDTO(schoolFormDto);
		schoolForm = this.repo.save(schoolForm);
		return schoolForm.getId();
	}

	public Collection<SchoolFormDto> getAllSchoolForms() {
		
		return  this.repo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	public Optional<SchoolFormDto> getSchoolFormById(Long id) {

		return this.mapper.toDTO(repo.findById(id));
	}

	public void deleteSchoolForm(Long id) {

		Optional<SchoolForm> opt = this.repo.findById(id);
		opt.ifPresent(schoolForm -> {
			schoolForm.remove();
			this.repo.save(schoolForm);
		});

	}

}
