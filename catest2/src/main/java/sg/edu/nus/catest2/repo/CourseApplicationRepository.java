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
	
	@Query("select ca from CourseApplication ca where ca.course.courseId = :courseId and ca.status = :status")
	List<CourseApplication> getCourseApplicationsByCourseIdAndStatus(@Param("courseId") int courseId,
			@Param("status") String status);
	
	CourseApplication getCourseApplicationByApplicationId(int applicationId);
	
	//List<CourseApplication> getApprovedCourseByStudentId(Student student);
		@Query("select ca from CourseApplication ca where ca.student.studentId = :studentId AND ca.status='Approved'")
		List<CourseApplication> getApprovedCourseByStudentId(@Param("studentId") int studentId);
		
		@Query("select ca from CourseApplication ca where ca.student.studentId = :studentId AND ca.status='Pending'")
		List<CourseApplication> getPendingCourseByStudentId(int studentId);

		@Query("SELECT ca FROM CourseApplication ca WHERE ca.course.courseId= :courseId AND ca.student.studentId= :stuId" )
		CourseApplication findByCourseAndStudent(	@Param("courseId") int courseId, 
													@Param("stuId") int stuId);

		@Query("select ca from CourseApplication ca where ca.student.studentId = :studentId AND ca.status='Rejected'")
		List<CourseApplication> getRejectCourseByStudentId(int studentId);

}
