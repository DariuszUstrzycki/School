package pl.ust.school.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.ust.school.controller.RecordNotFoundException;
import pl.ust.school.dto.SchoolformDto;
import pl.ust.school.dto.TeacherSubjectDto;
import pl.ust.school.entity.Schoolform;
import pl.ust.school.entity.TeacherSubject;
import pl.ust.school.mapper.SchoolformMapper;
import pl.ust.school.mapper.TeacherSubjectMapper;
import pl.ust.school.repository.SchoolformRepository;

@Service
public class SchoolformServiceImpl implements SchoolformService {

	@Autowired
	private SchoolformRepository schoolformRepo;
	
	@Autowired
	private SchoolformMapper schoolformMapper;
	
	@Autowired
	private TeacherSubjectService teacherSubjectService;
	
	@Autowired
	private TeacherSubjectMapper teacherSubjectMapper;
	

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
	public Collection<TeacherSubjectDto> getNotTaughtTeacherSubjects(SchoolformDto schoolformDto) {

		Collection<TeacherSubject> teacherSubjectsFromSchoolform = schoolformDto.getTeacherSubjects();
		Collection<TeacherSubject> all = this.teacherSubjectService.getAllTeacherSubjects();
		all.removeAll(teacherSubjectsFromSchoolform);

		return all.stream()
				.map(teacherSubjectMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public Collection<SchoolformDto> getAllSchoolformDtos() {

		return this.schoolformRepo.findAll()
				.stream()
				.map(schoolformMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public void removeTeacherSubject(long schoolformId, long teacherSubjectId) {
		//Optional<TeacherSubject> opt = this.teacherSubjectRepository.findById(teacherSubjectId);
		
		Optional<Schoolform> opt = this.schoolformRepo.findById(schoolformId);

		if (opt.isPresent()) {
			/*TeacherSubject teacherSubject = opt.get();
			teacherSubject.setSchoolform(null);
			this.teacherSubjectRepository.save(teacherSubject);*/
			Schoolform schoolform = opt.get();
			TeacherSubject toBeRemoved = null;
			for(TeacherSubject ts : schoolform.getTeacherSubjects()) {
				if(ts.getId() == teacherSubjectId) {
					toBeRemoved = ts;
				}
			}
			
			if(toBeRemoved != null) {
				schoolform.removeTeacherSubject(toBeRemoved);
				this.schoolformRepo.save(schoolform);
			}
			
			
		} else {
			throw new RecordNotFoundException("No schoolform with id " + teacherSubjectId + " has been found.");
		}
		
	}

	@Override
	public void addTeacherSubject(long schoolformId, long teacherSubjectId) {
		// TODO Auto-generated method stub
		
	}

}
