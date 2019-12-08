package sg.edu.nus.catest2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.catest2.model.*;

public interface CourseApplicationRepository extends JpaRepository<CourseApplication, Integer> {
	
	@Query("select ca from CourseApplication ca where ca.student.studentId = :studentId")
	List<CourseApplication> getCourseApplicationsByStudentId(@Param("studentId") int studentId);
	
	@Query("select ca from CourseApplication ca where ca.course.courseId = :courseId")
	List<CourseApplication> getCourseApplicationsByCourseId(@Param("courseId") int courseId);
	
	CourseApplication getCourseApplicationByApplicationId(int applicationId);

}
