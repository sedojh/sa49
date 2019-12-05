package sg.edu.nus.catest2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.catest2.model.*;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Admin getByAdminId(int adminId);

}
