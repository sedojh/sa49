package sg.edu.nus.catest2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.catest2.model.*;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	Course getCourseByCourseId(int courseId);
	
	@Query("select c from Course c where c.courseName like %?1% or c.courseCode like %?1%")
	List<Course> searchCourse(String name);
	
	@Query("select c from Course c where c.faculty.facultyId = :facultyId")
	List<Course> getCoursesByFacultyId(@Param("facultyId") int facultyId);
	
	@Query("select c from Course c where c.department.departmentId = :departmentId")
	List<Course> getCoursesByDepartmentId(@Param("departmentId") int departmentId);

}
