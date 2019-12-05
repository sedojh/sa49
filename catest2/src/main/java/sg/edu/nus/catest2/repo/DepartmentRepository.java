package sg.edu.nus.catest2.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.catest2.model.*;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	
}
