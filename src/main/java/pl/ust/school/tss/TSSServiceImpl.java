package pl.ust.school.tss;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.system.RecordNotFoundException;

@Service
public class TSSServiceImpl implements TSSService{
	
	@Autowired
	private TSSRepository tSSRepo;
	
	@Autowired
	private TSSMapper tSSMapper;

	@Override
	public Optional<TSS> getTSS(long teacherId, long subjectId) {
		return this.tSSRepo.findByTeacherIdAndSubjectId(teacherId, subjectId);
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
	public void deleteTSS(long tSSId) {
		
		Optional<TSS> opt = this.tSSRepo.findById(tSSId);
		
		if(opt.isPresent()) {
			TSS tss = opt.get();
			tss.removeTSS();
			this.tSSRepo.delete(tss);
		} else {
			throw new RecordNotFoundException("No TSS with id " + tSSId + " has been found.");
		} 
		
	}
	
	@Override
	public void deleteTSSsBySubject(long subjectId) {
		
		Collection<TSS> tssesWithThisSubject = this.tSSRepo.findBySubjectId(subjectId);
		
		for(TSS tss : tssesWithThisSubject) {
			tss.removeTSS();
			this.tSSRepo.delete(tss);
		}
		
	}
	
	@Override
	public Optional<TSS> getTSS(long tSSId) {
		return this.tSSRepo.findById(tSSId);
	}


	@Override
	public TSSDto getTSSDto(long tSSId) {
		
		Optional<TSS> opt = this.tSSRepo.findById(tSSId);
		
		if(opt.isPresent()) {
			
			TSS tss = opt.get();
			return this.tSSMapper.toDTO(tss);
			
		} else {
			throw new RecordNotFoundException("No TSS with id " + tSSId + " has been found.");
		} 
		
	}

}
