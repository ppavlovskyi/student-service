package telran.java52.student.controller;

import java.util.List;
import java.util.Set;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.student.dto.ScoreDto;
import telran.java52.student.dto.StudentAddDto;
import telran.java52.student.dto.StudentDto;
import telran.java52.student.dto.StudentUpdateDto;
import telran.java52.student.service.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentController {

	final StudentService stdentService;

	@PostMapping("/student")
	public Boolean addStudent(@RequestBody StudentAddDto studentAddDto) {
		return stdentService.addStudent(studentAddDto);
	}

	@GetMapping("/student/{studentId}")
	public StudentDto findStudent(@PathVariable("studentId") Long id) {
		return stdentService.findStudent(id);
	}

	@DeleteMapping("/student/{id}")
	public StudentDto removeStudent(@PathVariable Long id) {
		return stdentService.removeStudent(id);
	}

	@PutMapping("/student/{id}")
	public StudentAddDto updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDto studentUpdateDto) {
		return stdentService.updateStudent(id, studentUpdateDto);
	}

	@PutMapping("/score/student/{id}")
	public Boolean addScore(@PathVariable Long id, @RequestBody ScoreDto scoreDto) {

		return stdentService.addScore(id, scoreDto);
	}

	@GetMapping("/students/name/{name}")
	public List<StudentDto> findStudentsByName(@PathVariable String name) {
		return stdentService.findStudentsByName(name);
	}

	@PostMapping("/quantity/students")
	public Integer getStudentsNamesQuantity(@RequestBody Set<String> names) {
		return stdentService.getStudentsNamesQuantity(names);
	}

	@GetMapping("/students/exam/{exam}/minscore/{minScore}")
	public List<StudentDto> getStudentsByExamMinScore(@PathVariable String exam, @PathVariable Integer minScore) {
		return stdentService.getStudentsByExamMinScore(exam, minScore);
	}

}
