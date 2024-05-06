package telran.java52.student.service;

import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java52.student.dao.StudentRepository;
import telran.java52.student.dto.ScoreDto;
import telran.java52.student.dto.StudentAddDto;
import telran.java52.student.dto.StudentDto;
import telran.java52.student.dto.StudentUpdateDto;
import telran.java52.student.dto.exeption.StudentNotFoundException;
import telran.java52.student.model.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	
	final ModelMapper modelMapper;
	final StudentRepository studentRepository;

	@Override
	public Boolean addStudent(StudentAddDto studentAddDto) {
		if (studentRepository.existsById(studentAddDto.getId())) {
			return false;
		}
//		Student student = new Student(studentAddDto.getId(), studentAddDto.getName(), studentAddDto.getPassword());
	Student student = modelMapper.map(studentAddDto, Student.class);
		studentRepository.save(student);
		return true;
	}

	@Override
	public StudentDto findStudent(Long id) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);

		return modelMapper.map(student, StudentDto.class);
//		return new StudentDto(id, student.getName(), student.getScores());
	}

	@Override
	public StudentDto removeStudent(Long id) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		studentRepository.deleteById(id);
//		return new StudentDto(id, student.getName(), student.getScores());
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentAddDto updateStudent(Long id, StudentUpdateDto studentUpdateDto) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		if (studentUpdateDto.getName() != null && !studentUpdateDto.getName().equals("")
				&& !studentUpdateDto.getName().equals(student.getName())) {
			student.setName(studentUpdateDto.getName());
		}
		if (studentUpdateDto.getPassword() != null && !studentUpdateDto.getPassword().equals("")
				&& !studentUpdateDto.getPassword().equals(student.getPassword())) {
			student.setPassword(studentUpdateDto.getPassword());
		}
		studentRepository.save(student);
//		return new StudentAddDto(student.getId(), student.getName(), student.getPassword());
		return modelMapper.map(student, StudentAddDto.class);
	}

	@Override
	public Boolean addScore(Long id, ScoreDto scoreDto) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		boolean res = student.addScore(scoreDto.getExamName(), scoreDto.getScore());
		studentRepository.save(student);
		return res;
	}

	@Override
	public List<StudentDto> findStudentsByName(String name) {
		List<StudentDto> reStudentDtos = studentRepository.findStudentsByNameIgnoreCase(name)
				.map(s -> modelMapper.map(s, StudentDto.class))
				.toList();
		return reStudentDtos;
	}

	@Override
	public Integer getStudentsNamesQuantity(Set<String> names) {

//		return (int) studentRepository.findAll().stream()
//				.filter(s -> names.stream().anyMatch(n -> n.equalsIgnoreCase(s.getName()))).count();
		return studentRepository.countStudentsByNameInIgnoreCase(names);
	}

	@Override
	public List<StudentDto> getStudentsByExamMinScore(String exam, Integer minScore) {

//		return studentRepository.findAll().stream()
//				.filter(s -> s.getScores().containsKey(exam) && s.getScores().get(exam) >= minScore)
//				.map(s -> new StudentDto(s.getId(), s.getName(), s.getScores())).collect(Collectors.toList());
	
		return studentRepository.findByExamAndScoreGreaterThen(exam, minScore)
				.map(s -> new StudentDto(s.getId(), s.getName(), s.getScores())).collect(Collectors.toList());
	}

}
