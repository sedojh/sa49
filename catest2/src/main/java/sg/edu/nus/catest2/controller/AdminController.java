package sg.edu.nus.catest2.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import sg.edu.nus.catest2.repo.*;
import sg.edu.nus.catest2.model.*;
import sg.edu.nus.catest2.mvcmodel.*;
import sg.edu.nus.catest2.service.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminRepository arepo;
	@Autowired
	CourseApplicationRepository carepo;
	@Autowired
	DepartmentRepository drepo;
	@Autowired
	CourseRepository crepo;
	@Autowired
	StudentRepository srepo;
	@Autowired
	FacultyRepository frepo;
	@Autowired
	CourseApplicationService caserv;
	@Autowired
	DepartmentService dserv;
	@Autowired
	StudentService sserv;
	@Autowired
	FacultyService fserv;
	@Autowired
	CourseService cserv;
	
	private static Admin admin = new Admin();
	
	@RequestMapping("/home")
	public String home(Model model,@SessionAttribute("usersession") Session session,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size) {
		
		if(session.getSessionId() == 0) {
			return "redirect:/login/welcome";
		}
		
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		
		admin = arepo.getByAdminId(session.getSessionId());
		model.addAttribute("admin", admin);
		List<CourseApplication> courseApplications = caserv.getPendingCourseApplications();
		if(courseApplications.isEmpty()) {
			String msg = "null";
			model.addAttribute("msg", msg);
		}
		else {
			model.addAttribute("courseApplications", courseApplications);
			String msg = "found";
			model.addAttribute("msg", msg);
			
			Page<CourseApplication> courseApplicationPage = caserv.findPaginatedCourseApplication
					(PageRequest.of(currentpage - 1, pagesize),courseApplications);
	        model.addAttribute("courseApplicationPage", courseApplicationPage);
	 
	        int totalPages = courseApplicationPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
		}
		
		return "adminhome";
	}
	
	@RequestMapping("/departmentlist")
	public String departmentlist(
			Model model,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "name") Optional<String> name) {
		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");
		
		if(searchname.equals("null")) {
			List<Department> departments = drepo.findAll();
			model.addAttribute("departments", departments);
			model.addAttribute("searched", "null");
			Page<Department> departmentPage = dserv.findPaginatedDepartment
					(PageRequest.of(currentpage - 1, pagesize),departments);
	        model.addAttribute("departmentPage", departmentPage);
	        int totalPages = departmentPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "departmentlist";
		}
		else {
			List<Department> departments = drepo.searchDepartment(searchname);
			if(departments.isEmpty()) {
				model.addAttribute("found", "nil");
			}
			else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("departments", departments);
			model.addAttribute("searched", searchname);
			Page<Department> departmentPage = dserv.findPaginatedDepartment
					(PageRequest.of(currentpage - 1, pagesize),departments);
	        model.addAttribute("departmentPage", departmentPage);
	        int totalPages = departmentPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "departmentlist";
		}
	}
	
	@RequestMapping("/studentlist")
	public String studentlist(
			Model model,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "name") Optional<String> name){
	
		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");
		
		if(searchname.equals("null")) {
			List<Student> students = srepo.findAll();
			model.addAttribute("students", students);
			model.addAttribute("searched", "null");
			Page<Student> studentPage = sserv.findPaginatedStudent
					(PageRequest.of(currentpage - 1, pagesize),students);
	        model.addAttribute("studentPage", studentPage);
	        int totalPages = studentPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "studentlist";
		}
		else {
			List<Student> students = srepo.searchStudent(searchname);
			if(students.isEmpty()) {
				model.addAttribute("found", "nil");
			}
			else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("students", students);
			model.addAttribute("searched", searchname);
			Page<Student> studentPage = sserv.findPaginatedStudent
					(PageRequest.of(currentpage - 1, pagesize),students);
	        model.addAttribute("studentPage", studentPage);
	        int totalPages = studentPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "studentlist";
		}
		
	}
	
	@RequestMapping("/facultylist")
	public String facultylist(
			Model model,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "name") Optional<String> name) {
		
		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");
		if(searchname.equals("null")) {
			List<Faculty> faculties = frepo.findAll();
			model.addAttribute("faculties", faculties);
			model.addAttribute("searched", "null");
			Page<Faculty> facultyPage = fserv.findPaginatedFaculty
					(PageRequest.of(currentpage - 1, pagesize),faculties);
	        model.addAttribute("facultyPage", facultyPage);
	        int totalPages = facultyPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "facultylist";
		}
		else {
			List<Faculty> faculties = frepo.searchFaculty(searchname);
			if(faculties.isEmpty()) {
				model.addAttribute("found", "nil");
			}
			else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("faculties", faculties);
			model.addAttribute("searched", searchname);
			Page<Faculty> facultyPage = fserv.findPaginatedFaculty
					(PageRequest.of(currentpage - 1, pagesize),faculties);
	        model.addAttribute("facultyPage", facultyPage);
	        int totalPages = facultyPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "facultylist";
		}
	}
	
	@RequestMapping("/courselist")
	public String courselist(
			Model model,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "name") Optional<String> name) {
		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");
		if(searchname.equals("null")) {
			List<Course> courses = crepo.findAll();
			model.addAttribute("courses", courses);
			model.addAttribute("searched", "null");
			Page<Course> coursePage = cserv.findPaginatedCourse
					(PageRequest.of(currentpage - 1, pagesize),courses);
	        model.addAttribute("coursePage", coursePage);
	        int totalPages = coursePage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "courselist";
		}
		else {
			List<Course> courses = crepo.searchCourse(searchname);
			if(courses.isEmpty()) {
				model.addAttribute("found", "nil");
			}
			else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("courses", courses);
			model.addAttribute("searched", searchname);
			Page<Course> coursePage = cserv.findPaginatedCourse
					(PageRequest.of(currentpage - 1, pagesize),courses);
	        model.addAttribute("coursePage", coursePage);
	        int totalPages = coursePage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "courselist";
		}
	}
	
	@RequestMapping("/courseapplicationlist")
	public String courseApplicationlist(
			Model model,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "sort") Optional<String> sort) {
		model.addAttribute("admin", admin);
		
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String sorting = sort.orElse("all");
		
		if(sorting.equals("approved")) {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = caserv.getApprovedCourseApplications();
			if(courseApplications.isEmpty()) {
				String msg = "null";
				model.addAttribute("msg", msg);
			}
			else {
				model.addAttribute("courseApplications", courseApplications);
				Page<CourseApplication> courseApplicationPage = caserv.findPaginatedCourseApplication
						(PageRequest.of(currentpage - 1, pagesize),courseApplications);
		        model.addAttribute("courseApplicationPage", courseApplicationPage);
		 
		        int totalPages = courseApplicationPage.getTotalPages();
		        if (totalPages > 0) {
		            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
		                .boxed()
		                .collect(Collectors.toList());
		            model.addAttribute("pageNumbers", pageNumbers);
		        }
				
			}
	        return "courseapplicationlist";
		}
		else if(sorting.equals("pending")) {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = caserv.getPendingCourseApplications();
			if(courseApplications.isEmpty()) {
				String msg = "null";
				model.addAttribute("msg", msg);
			}
			else {
				model.addAttribute("courseApplications", courseApplications);
				Page<CourseApplication> courseApplicationPage = caserv.findPaginatedCourseApplication
						(PageRequest.of(currentpage - 1, pagesize),courseApplications);
		        model.addAttribute("courseApplicationPage", courseApplicationPage);
		 
		        int totalPages = courseApplicationPage.getTotalPages();
		        if (totalPages > 0) {
		            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
		                .boxed()
		                .collect(Collectors.toList());
		            model.addAttribute("pageNumbers", pageNumbers);
		        }
			}
	        return "courseapplicationlist";
		}
		else {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = carepo.findAll();
			if(courseApplications.isEmpty()) {
				String msg = "null";
				model.addAttribute("msg", msg);
			}
			else {
				model.addAttribute("courseApplications", courseApplications);
				Page<CourseApplication> courseApplicationPage = caserv.findPaginatedCourseApplication
						(PageRequest.of(currentpage - 1, pagesize),courseApplications);
		        model.addAttribute("courseApplicationPage", courseApplicationPage);
		 
		        int totalPages = courseApplicationPage.getTotalPages();
		        if (totalPages > 0) {
		            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
		                .boxed()
		                .collect(Collectors.toList());
		            model.addAttribute("pageNumbers", pageNumbers);
		        }
			}
	        return "courseapplicationlist";
		}
		
	}
	
	
}
