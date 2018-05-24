package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.Student;
import pl.ust.school.entity.TSS;
import pl.ust.school.mapper.TSSMapper;
import pl.ust.school.repository.TSSRepository;

@Service
public class TSSServiceImpl implements TSSService {
	
	@Autowired
	private TSSRepository tsRepo;
	
	@Autowired
	private TSSMapper mapper;

	///////////////////////////////////////////////
	
	@Override
	public Collection<TSSDto> getAllTSSs() {
		
		return  this.tsRepo.findByOrderBySchoolformAsc()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public Collection<TSSDto> getTSSsBySchoolformId(long id) {
		return this.tsRepo.findTSSBySchoolformIdOrderBySubjectAsc(id)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Collection<TSSDto> getTSSsByTeacherId(long id) {
		return this.tsRepo.findTSSByTeacherIdOrderBySubjectAsc(id)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Collection<TSSDto> getTSSsBySubjectId(long id) {
		return this.tsRepo.findTSSBySubjectIdOrderByTeacherAsc(id)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public long createTSS(TSSDto tSSDto) {
		TSS tSS = this.mapper.fromDTO(tSSDto);
		tSS = this.tsRepo.save(tSS);
		return tSS.getId();
	}

	@Override
	public Optional<TSSDto> getTSSById(long id) {
		return this.mapper.toDTO(this.tsRepo.findById(id));
	}

	@Override
	public void deleteTSS(long id) {
		Optional<TSS> opt = this.tsRepo.findById(id);
		opt.ifPresent(tSS -> {
			tSS.remove();
			this.tsRepo.save(tSS);
		});
	}


	


	
	
	

	

}
