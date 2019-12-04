package sg.edu.nus.catest2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.catest2.model.*;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
