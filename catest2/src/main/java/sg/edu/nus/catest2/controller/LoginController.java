package sg.edu.nus.catest2.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import sg.edu.nus.catest2.mvcmodel.*;
import sg.edu.nus.catest2.repo.*;
import sg.edu.nus.catest2.model.*;

@Controller
@SessionAttributes("usersession")
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UserRepository urepo;
	
	@ModelAttribute("usersession")
	public Session session() {
		return new Session();
	}

	@GetMapping("/welcome")
	public String welcome(Model model) {
		model.addAttribute("login", new Login());
		return "welcome";
	}

	@PostMapping("/welcome")
	public String trylogin(@ModelAttribute Login login, Model model1,
			@ModelAttribute("usersession") Session sessionobj) {
		if (login.getUsername() == "" || login.getPassword() == "") {
			String msg = "Both fields cannot be empty";
			model1.addAttribute("msg", msg);
			return "welcome";
		}

		else {
			String username = login.getUsername();
			String password = login.getPassword();
			User user = urepo.findByUserName(username);
			if (user == null) {
				String msg = "Username/Password is wrong";
				model1.addAttribute("msg", msg);
				return "welcome";
			} else if (user.getPassword().equals(password)) {
				if (user.getAdminId() == 0 && user.getFacultyId() == 0) {
					sessionobj.setSessionId(user.getStudentId());
					sessionobj.setUserType("student");
					return "redirect:/student/home";
				} else if (user.getAdminId() == 0 && user.getStudentId() == 0) {
					sessionobj.setSessionId(user.getFacultyId());
					sessionobj.setUserType("faculty");
					return "redirect:/faculty/home";
				} else {
					sessionobj.setSessionId(user.getAdminId());
					sessionobj.setUserType("admin");
					return "redirect:/admin/home";
				}
			} else {
				return "welcome";
			}
		}
	}

	@RequestMapping("/logout")
	public String logout(@ModelAttribute("usersession") Session session, 
			WebRequest request, SessionStatus status) {
		status.setComplete();
		request.removeAttribute("user", WebRequest.SCOPE_SESSION);
		return "redirect:/login/welcome";
	}
}
