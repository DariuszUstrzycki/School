package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.dto.TSSDto;
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
		
		return  this.tsRepo.findAll()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toSet());
	}
	
	
	@Override
	public Collection<TSSDto> getTSSsBySchoolformId(long id) {
		return this.tsRepo.findTSSBySchoolformId(id)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toSet());
	}
	
	@Override
	public Collection<TSSDto> getTSSsByTeacherId(long id) {
		return this.tsRepo.findTSSByTeacherId(id)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toSet());
	}
	
	@Override
	public Collection<TSSDto> getTSSsBySubjectId(long id) {
		return this.tsRepo.findTSSBySubjectId(id)
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toSet());
	}


	@Override
	public long createTSS(TSSDto tsDto) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Optional<TSSDto> getTSSById(long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteTSS(long id) {
		// TODO Auto-generated method stub
		
	}


	


	
	
	

	

}
