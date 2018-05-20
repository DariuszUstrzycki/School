package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.SubjectDto;
import pl.ust.school.entity.Subject;
import pl.ust.school.mapper.SubjectMapper;
import pl.ust.school.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository repo;

	@Autowired
	private SubjectMapper mapper;

	public void createSubject(SubjectDto subjectDto) {
		Subject subject = this.mapper.fromDTO(subjectDto);
		this.repo.save(subject);
	}

	public Collection<SubjectDto> getAllSubjects() {
		
		return  this.repo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
		
		 /*Collection<Subject> subjects = repo.findAll();
		 Collection<SubjectDto> subjectDtos = new HashSet<>();
		
		 for (Subject s : subjects) {
		 subjectDtos.add(this.mapper.toDTO(s));
		 }*/
	}

	public Optional<SubjectDto> getSubjectById(Long id) {

		return this.mapper.toDTO(repo.findById(id));
	}

	public void deleteSubject(Long id) {

		Optional<Subject> opt = this.repo.findById(id);
		opt.ifPresent(subject -> {
			subject.remove();
			this.repo.save(subject);
		});

	}

}
