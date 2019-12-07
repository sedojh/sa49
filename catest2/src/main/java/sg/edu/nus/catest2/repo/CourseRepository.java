package sg.edu.nus.catest2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.catest2.model.*;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	Course getCourseByCourseId(int courseId);
	
	@Query("select c from Course c where c.courseName like %?1% or c.courseCode like %?1%")
	List<Course> searchCourse(String name);

}
