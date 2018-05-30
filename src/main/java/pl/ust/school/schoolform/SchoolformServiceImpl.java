package pl.ust.school.schoolform;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.system.RecordNotFoundException;
import pl.ust.school.tss.TSS;
import pl.ust.school.tss.TSSDto;
import pl.ust.school.tss.TSSMapper;
import pl.ust.school.tss.TSSService;

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
	
	public void deleteSchoolform(Long id) {
		
		//////////////////// workinf area ///////////////////////

		Optional<Schoolform> opt = this.schoolformRepo.findById(id);

		if (opt.isPresent()) {
			Schoolform schoolform = opt.get();
			schoolform.removeAllStudents();
			for(TSS tss : schoolform.getTsses()) {
				tss.setSchoolform(null);
			}
			schoolform.getTsses().clear();
			this.schoolformRepo.delete(schoolform);
		} else {
			throw new RecordNotFoundException("No schoolform with id " + id + " has been found.");
		}
	}

	@Override
	public void removeSchoolformFromTSS(long schoolformId, long tSSId) {
		
		
		
		Optional<TSS> tssOpt = this.tSSService.getTSS(tSSId);
		if (tssOpt.isPresent()) {
			TSS tss = tssOpt.get();
			Schoolform schoolform = tss.getSchoolform();
			tss.setSchoolform(null);
			this.schoolformRepo.save(schoolform);
		} else {
			throw new RecordNotFoundException("No TSS with id " + tSSId + " has been found.");
		}	
		
	}

	@Override
	public void addSchoolformToTSS(long schoolformId, long tSSId) {
		Optional<TSS> tssOpt = this.tSSService.getTSS(tSSId);

		if (tssOpt.isPresent()) {
			TSS tss = tssOpt.get();

			Optional<Schoolform> schOpt = this.schoolformRepo.findById(schoolformId);
			if (schOpt.isPresent()) {
				Schoolform schoolform = schOpt.get();
				tss.setSchoolform(schoolform);
				this.schoolformRepo.save(schoolform);
			} else {
				throw new RecordNotFoundException("No schoolform with id " + schoolformId + " has been found.");

			}

		} 
	}

}
