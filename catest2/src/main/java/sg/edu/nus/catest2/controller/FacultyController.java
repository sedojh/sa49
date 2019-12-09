package sg.edu.nus.catest2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import sg.edu.nus.catest2.repo.*;
import sg.edu.nus.catest2.model.*;
import sg.edu.nus.catest2.mvcmodel.*;
import sg.edu.nus.catest2.service.*;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	FacultyRepository frepo;
	@Autowired
	CourseRepository crepo;
	@Autowired
	CourseApplicationRepository carepo;
	@Autowired
	StudentRepository srepo;
	@Autowired
	GradeRepository grepo;
	@Autowired
	GradeService gserv;
	
	private static Faculty faculty = new Faculty();
	
	@RequestMapping("/home")
	public String home(Model model, @SessionAttribute("usersession") Session session,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {

		if (session.getSessionId() == 0) {
			return "redirect:/login/welcome";
		}

		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);

		faculty = frepo.getByFacultyId(session.getSessionId());
		model.addAttribute("faculty", faculty);

		return "facultyhometest";
	}
	
	@RequestMapping("/info")
	public String info(@ModelAttribute("usertype") final User userobject,
			final BindingResult bindingResult,
			final Model model) {
		Faculty faculty = frepo.getByFacultyId(userobject.getFacultyId());
		model.addAttribute("faculty", faculty);
		return "info";
	}
	
	//testgrades
	@RequestMapping("/showgrades")
	public String showgrades(Model model,@RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size) {
		model.addAttribute("faculty", faculty);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		
		//get the course selected(the faculty user will click on a course and pass the courseId to this method)
		Course course = crepo.getCourseByCourseId(1);
		//get all courseapplications with courseId and status=approved
		List<CourseApplication> courseApplications = carepo.getCourseApplicationsByCourseIdAndStatus(course.getCourseId(), "Approved");
		//instantiate a list of grades
		List<Grade> grades = new ArrayList<>();
		//for each courseapplication(which are approved), add them to the grades list with studentId
		for(CourseApplication ca : courseApplications) {
			grades.add(grepo.getGradeByStudentId(ca.getStudent().getStudentId()));
		}
		//pass the grades list to the view by adding to the model
		model.addAttribute("grades", grades);
		
		//pagination
		Page<Grade> gradePage = gserv.findPaginatedGrade(PageRequest.of(currentpage - 1, pagesize),
				grades);
		model.addAttribute("gradePage", gradePage);
		int totalPages = gradePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "gradedisplaytest";
		
	}

}
