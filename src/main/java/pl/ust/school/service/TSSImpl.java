package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.TSS;
import pl.ust.school.mapper.TSSMapper;
import pl.ust.school.repository.TSSRepository;

@Service
public class TSSImpl implements TSSService{
	
	@Autowired
	private TSSRepository tSSRepo;
	
	@Autowired
	private TSSMapper tSSMapper;

	@Override
	public Optional<TSS> getTSS(long teacherId, long subjectId) {
		return this.tSSRepo.findByTeacherIdAndSubjectId(teacherId, subjectId);
	}

	@Override
	public void delete(long tSSId) {
		this.tSSRepo.deleteById(tSSId);
	}

	@Override
	public Collection<TSS> getAllTSSs() {
		return this.tSSRepo.findAll();
	}

	@Override
	public Collection<TSSDto> getAllTSSDtos() {
		return this.tSSRepo.findAll()
				.stream()
				.map(tSSMapper::toDTO)
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<TSS> getTSS(long tSSId) {
		return this.tSSRepo.findById(tSSId);
	}

}
