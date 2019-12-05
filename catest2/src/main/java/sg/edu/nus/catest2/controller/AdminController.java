package sg.edu.nus.catest2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import sg.edu.nus.catest2.repo.*;
import sg.edu.nus.catest2.model.*;
import sg.edu.nus.catest2.mvcmodel.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminRepository arepo;
	
	private static Admin admin = new Admin();
	
	@RequestMapping("/info")
	public String info(Model model,@SessionAttribute("usersession") Session session) {
		admin = arepo.getByAdminId(session.getSessionId());
		model.addAttribute("admin", admin);
		return "info";
	}

}
