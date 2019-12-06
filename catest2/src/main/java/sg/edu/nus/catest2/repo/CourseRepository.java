package sg.edu.nus.catest2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.catest2.model.*;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	Course getCourseByCourseId(int courseId);

}
