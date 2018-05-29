package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.controller.RecordNotFoundException;
import pl.ust.school.dto.SchoolformDto;
import pl.ust.school.dto.TSSDto;
import pl.ust.school.entity.Schoolform;
import pl.ust.school.entity.TSS;
import pl.ust.school.mapper.SchoolformMapper;
import pl.ust.school.mapper.TSSMapper;
import pl.ust.school.repository.SchoolformRepository;

@Service
public class SchoolformServiceImpl implements SchoolformService {

	@Autowired
	private SchoolformRepository schoolformRepo;
	
	@Autowired
	private SchoolformMapper schoolformMapper;
	
	@Autowired
	private TSSService tSSService;
	
	@Autowired
	private TSSMapper tSSMapper;
	

	public long createSchoolform(SchoolformDto schoolformDto) {
		Schoolform schoolform = this.schoolformMapper.fromDTO(schoolformDto);
		schoolform = this.schoolformRepo.save(schoolform);
		return schoolform.getId();
	}

	public Optional<SchoolformDto> getSchoolformDtoById(long id) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			return this.schoolformMapper.toDTO(opt);
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}
	}

	@Override
	public Optional<Schoolform> getSchoolformById(long id) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			return opt;
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}

	}

	public void deleteSchoolform(Long id) {

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			Schoolform schoolform = opt.get();
			schoolform.removeAllStudents();
			this.schoolformRepo.delete(schoolform);
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}
	}
	
	///////////////////////////////////////////

	@Override
	public Collection<TSSDto> getNotTaughtTSSs(SchoolformDto schoolformDto) {

		Collection<TSS> tssesFromSchoolform = schoolformDto.getTsses();
		Collection<TSS> all = this.tSSService.getAllTSSs();
		all.removeAll(tssesFromSchoolform);

		return all.stream()
				.map(tSSMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public Collection<SchoolformDto> getAllSchoolformDtos() {

		return this.schoolformRepo.findAll()
				.stream()
				.map(schoolformMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public void removeTSS(long schoolformId, long tSSId) {
		//Optional<TSS> opt = this.tSSRepository.findById(tSSId);
		
		Optional<Schoolform> opt = this.schoolformRepo.findById(schoolformId);

		if (opt.isPresent()) {
			/*TSS tSS = opt.get();
			tSS.setSchoolform(null);
			this.tSSRepository.save(tSS);*/
			Schoolform schoolform = opt.get();
			TSS toBeRemoved = null;
			for(TSS ts : schoolform.getTsses()) {
				if(ts.getId() == tSSId) {
					toBeRemoved = ts;
				}
			}
			
			if(toBeRemoved != null) {
				schoolform.removeTSS(toBeRemoved);
				this.schoolformRepo.save(schoolform);
			}
			
			
		} else {
			throw new RecordNotFoundException("No schoolform with id " + tSSId + " has been found.");
		}
		
	}

	@Override
	public void addTSS(long schoolformId, long tSSId) {
		// TODO Auto-generated method stub
		
	}

}
