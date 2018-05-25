package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.SchoolformDto;
import pl.ust.school.entity.Schoolform;
import pl.ust.school.mapper.SchoolformMapper;
import pl.ust.school.repository.SchoolformRepository;

@Service
public class SchoolformServiceImpl implements SchoolformService{

	@Autowired
	private SchoolformRepository repo;

	@Autowired
	private SchoolformMapper mapper;

	public long  createSchoolform(SchoolformDto schoolformDto) {
		Schoolform schoolform = this.mapper.fromDTO(schoolformDto);
		schoolform = this.repo.save(schoolform);
		return schoolform.getId();
	}

	public Collection<SchoolformDto> getAllSchoolforms() {
		
		return  this.repo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	public Optional<SchoolformDto> getSchoolformById(Long id) {

		return this.mapper.toDTO(repo.findById(id));
	}

	public void deleteSchoolform(Long id) {
/*
		Optional<Schoolform> opt = this.repo.findById(id);
		opt.ifPresent(schoolform -> {
			schoolform.remove();
			this.repo.save(schoolform);
		});
*/
	}

}
