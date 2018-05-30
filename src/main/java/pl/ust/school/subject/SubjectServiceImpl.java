package pl.ust.school.subject;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.system.RecordNotFoundException;
import pl.ust.school.tss.TSSService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private TSSService tSSService;

	@Autowired
	private SubjectMapper mapper;

	public long createSubject(SubjectDto subjectDto) {
		Subject subject = this.mapper.fromDTO(subjectDto);
		subject = this.subjectRepo.save(subject);
		return subject.getId();
	}

	public Collection<SubjectDto> getAllSubjectDtos() {

		return this.subjectRepo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toSet());
	}

	public Optional<SubjectDto> getSubjectDtoById(long id) {

		Optional<Subject> opt = this.subjectRepo.findById(id);

		if (opt.isPresent()) {
			return this.mapper.toDTO(opt);
		} else {
			throw new RecordNotFoundException("No subject with id " + id + " has been found.");
		}
	}

	public Optional<Subject> getSubjectById(long id) {

		Optional<Subject> opt = this.subjectRepo.findById(id);

		if (opt.isPresent()) {
			return opt;
		} else {
			throw new RecordNotFoundException("No subject with id " + id + " has been found.");
		}
	}

	@Override
	public void deleteSubject(long subjectId) {
		
		Optional<Subject> opt = this.subjectRepo.findById(subjectId);

		if (opt.isPresent()) {
			this.tSSService.deleteTSSsBySubject(subjectId);
			this.subjectRepo.delete(opt.get());
		} else {
			throw new RecordNotFoundException("No subject with id " + subjectId + " has been found.");
		}
				
	}
}
