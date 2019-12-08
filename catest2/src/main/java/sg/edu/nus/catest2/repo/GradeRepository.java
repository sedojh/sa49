package sg.edu.nus.catest2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.catest2.model.*;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
	@Query("select g from Grade g where g.course.courseId = :courseId")
	List<Grade> getGradesByCourseId(@Param("courseId") int courseId);
	
	@Query("select g from Grade g where g.student.studentId = :studentId")
	List<Grade> getGradesByStudentId(@Param("studentId") int studentId);

}
