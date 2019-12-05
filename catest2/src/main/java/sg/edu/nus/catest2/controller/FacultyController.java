package sg.edu.nus.catest2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import sg.edu.nus.catest2.repo.*;
import sg.edu.nus.catest2.model.*;
import sg.edu.nus.catest2.mvcmodel.*;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	FacultyRepository frepo;
	
	@RequestMapping("/info")
	public String info(@ModelAttribute("usertype") final User userobject,
			final BindingResult bindingResult,
			final Model model) {
		Faculty faculty = frepo.getByFacultyId(userobject.getFacultyId());
		model.addAttribute("faculty", faculty);
		return "info";
	}

}
