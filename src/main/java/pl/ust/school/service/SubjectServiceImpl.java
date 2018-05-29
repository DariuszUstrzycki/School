package pl.ust.school.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.controller.RecordNotFoundException;
import pl.ust.school.dto.SubjectDto;
import pl.ust.school.entity.Subject;
import pl.ust.school.entity.Teacher;
import pl.ust.school.entity.TSS;
import pl.ust.school.mapper.SubjectMapper;
import pl.ust.school.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;

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
		
		// get all TSS with this subject ID
		// iterate over them with delete(TSS)

		Optional<Subject> opt = this.subjectRepo.findById(subjectId);

		if (opt.isPresent()) {
			Subject subject = opt.get();
			Set<Teacher> teachers = new HashSet<>();
			for (TSS ts : subject.getTSSs()) {
				teachers.add(ts.getTeacher());
			}

			for (Teacher t : teachers) {
				t.removeTSS(subject);
			}

			this.subjectRepo.delete(subject);

		} else {
			throw new RecordNotFoundException("No subject with id " + subjectId + " has been found.");
		}

	}
}
