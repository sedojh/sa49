package sg.edu.nus.catest2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sg.edu.nus.catest2.mvcmodel.*;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("/welcome")
	public String welcome(Model model) {
		model.addAttribute("login", new Login());
		return "welcome";
	}
	
	@PostMapping("/welcome")
	public String trylogin(@ModelAttribute Login login, Model model) {
		if(login.getUsername()=="" || login.getPassword()=="") {
			String msg = "Both fields cannot be empty";
			model.addAttribute("msg",msg);
			return "welcome";
		}
		return "result";
	}

}
