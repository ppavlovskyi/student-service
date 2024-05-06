package telran.java52.student.dao;

import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.java52.student.model.Student;

public interface StudentRepository extends MongoRepository<Student, Long> {
	Stream<Student>getAllBy();
	Stream<Student>findStudentsByNameIgnoreCase(String name);
	int countStudentsByNameInIgnoreCase(Set<String> names);
	
	@Query("{'scores.?0':{$gt:?1}}")
	Stream<Student> findByExamAndScoreGreaterThen(String exam, int score);
	
//	@Query("{'scores.:#{#exam}': {$gt: :#{#score}}}")
//	Stream<Student> findByExamAndScoreGreaterThen(@Param("exam")String exam, @Param("score")int score);
}
