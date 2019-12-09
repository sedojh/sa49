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
	GradeRepository grepo;
	@Autowired
	FacultyLeaveRepository flrepo;
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
	@Autowired
	GradeService gserv;

	private static Admin admin = new Admin();

	@RequestMapping("/home")
	public String home(Model model, @SessionAttribute("usersession") Session session,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {

		if (session.getSessionId() == 0) {
			return "redirect:/login/welcome";
		}

		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);

		admin = arepo.getByAdminId(session.getSessionId());
		model.addAttribute("admin", admin);
		List<CourseApplication> courseApplications = caserv.getPendingCourseApplications();
		if (courseApplications.isEmpty()) {
			String msg = "null";
			model.addAttribute("msg", msg);
		} else {
			model.addAttribute("courseApplications", courseApplications);
			String msg = "found";
			model.addAttribute("msg", msg);

			Page<CourseApplication> courseApplicationPage = caserv
					.findPaginatedCourseApplication(PageRequest.of(currentpage - 1, pagesize), courseApplications);
			model.addAttribute("courseApplicationPage", courseApplicationPage);

			int totalPages = courseApplicationPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
		}

		return "adminhome";
	}

	@RequestMapping("/departmentlist")
	public String departmentlist(Model model, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size, @RequestParam(name = "name") Optional<String> name) {
		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");

		if (searchname.equals("null")) {
			List<Department> departments = drepo.findAll();
			model.addAttribute("departments", departments);
			model.addAttribute("searched", "null");
			Page<Department> departmentPage = dserv.findPaginatedDepartment(PageRequest.of(currentpage - 1, pagesize),
					departments);
			model.addAttribute("departmentPage", departmentPage);
			int totalPages = departmentPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "departmentlist";
		} else {
			List<Department> departments = drepo.searchDepartment(searchname);
			if (departments.isEmpty()) {
				model.addAttribute("found", "nil");
			} else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("departments", departments);
			model.addAttribute("searched", searchname);
			Page<Department> departmentPage = dserv.findPaginatedDepartment(PageRequest.of(currentpage - 1, pagesize),
					departments);
			model.addAttribute("departmentPage", departmentPage);
			int totalPages = departmentPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "departmentlist";
		}
	}

	@RequestMapping("/studentlist")
	public String studentlist(Model model, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size, @RequestParam(name = "name") Optional<String> name) {

		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");

		if (searchname.equals("null")) {
			List<Student> students = srepo.findAll();
			model.addAttribute("students", students);
			model.addAttribute("searched", "null");
			Page<Student> studentPage = sserv.findPaginatedStudent(PageRequest.of(currentpage - 1, pagesize), students);
			model.addAttribute("studentPage", studentPage);
			int totalPages = studentPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "studentlist";
		} else {
			List<Student> students = srepo.searchStudent(searchname);
			if (students.isEmpty()) {
				model.addAttribute("found", "nil");
			} else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("students", students);
			model.addAttribute("searched", searchname);
			Page<Student> studentPage = sserv.findPaginatedStudent(PageRequest.of(currentpage - 1, pagesize), students);
			model.addAttribute("studentPage", studentPage);
			int totalPages = studentPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "studentlist";
		}

	}

	@RequestMapping("/facultylist")
	public String facultylist(Model model, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size, @RequestParam(name = "name") Optional<String> name) {

		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");
		if (searchname.equals("null")) {
			List<Faculty> faculties = frepo.findAll();
			model.addAttribute("faculties", faculties);
			model.addAttribute("searched", "null");
			Page<Faculty> facultyPage = fserv.findPaginatedFaculty(PageRequest.of(currentpage - 1, pagesize),
					faculties);
			model.addAttribute("facultyPage", facultyPage);
			int totalPages = facultyPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "facultylist";
		} else {
			List<Faculty> faculties = frepo.searchFaculty(searchname);
			if (faculties.isEmpty()) {
				model.addAttribute("found", "nil");
			} else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("faculties", faculties);
			model.addAttribute("searched", searchname);
			Page<Faculty> facultyPage = fserv.findPaginatedFaculty(PageRequest.of(currentpage - 1, pagesize),
					faculties);
			model.addAttribute("facultyPage", facultyPage);
			int totalPages = facultyPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "facultylist";
		}
	}

	@RequestMapping("/courselist")
	public String courselist(Model model, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size, @RequestParam(name = "name") Optional<String> name) {
		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");
		if (searchname.equals("null")) {
			List<Course> courses = crepo.findAll();
			model.addAttribute("courses", courses);
			model.addAttribute("searched", "null");
			Page<Course> coursePage = cserv.findPaginatedCourse(PageRequest.of(currentpage - 1, pagesize), courses);
			model.addAttribute("coursePage", coursePage);
			int totalPages = coursePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "courselist";
		} else {
			List<Course> courses = crepo.searchCourse(searchname);
			if (courses.isEmpty()) {
				model.addAttribute("found", "nil");
			} else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("courses", courses);
			model.addAttribute("searched", searchname);
			Page<Course> coursePage = cserv.findPaginatedCourse(PageRequest.of(currentpage - 1, pagesize), courses);
			model.addAttribute("coursePage", coursePage);
			int totalPages = coursePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "courselist";
		}
	}

	@RequestMapping("/gradelist")
	public String gradelist(Model model, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size, @RequestParam(name = "name") Optional<String> name) {
		model.addAttribute("admin", admin);
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String searchname = name.orElse("null");
		if (searchname.equals("null")) {
			List<Grade> grades = grepo.findAll();
			model.addAttribute("grades", grades);
			model.addAttribute("searched", "null");
			Page<Grade> gradePage = gserv.findPaginatedGrade(PageRequest.of(currentpage - 1, pagesize), grades);
			model.addAttribute("gradePage", gradePage);
			int totalPages = gradePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "gradelist";
		} else {
			List<Grade> grades = grepo.searchGrade(searchname);
			if (grades.isEmpty()) {
				model.addAttribute("found", "nil");
			} else {
				model.addAttribute("found", "found");
			}
			model.addAttribute("grades", grades);
			model.addAttribute("searched", searchname);
			Page<Grade> gradePage = gserv.findPaginatedGrade(PageRequest.of(currentpage - 1, pagesize), grades);
			model.addAttribute("gradePage", gradePage);
			int totalPages = gradePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "gradelist";
		}
	}

	@RequestMapping("/courseapplicationlist")
	public String courseApplicationlist(Model model, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size, @RequestParam(name = "sort") Optional<String> sort) {
		model.addAttribute("admin", admin);

		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String sorting = sort.orElse("all");

		if (sorting.equals("approved")) {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = caserv.getApprovedCourseApplications();
			if (courseApplications.isEmpty()) {
				String msg = "null";
				model.addAttribute("msg", msg);
			} else {
				model.addAttribute("courseApplications", courseApplications);
				Page<CourseApplication> courseApplicationPage = caserv
						.findPaginatedCourseApplication(PageRequest.of(currentpage - 1, pagesize), courseApplications);
				model.addAttribute("courseApplicationPage", courseApplicationPage);

				int totalPages = courseApplicationPage.getTotalPages();
				if (totalPages > 0) {
					List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
							.collect(Collectors.toList());
					model.addAttribute("pageNumbers", pageNumbers);
				}
				String msg = "found";
				model.addAttribute("msg", msg);

			}
			return "courseapplicationlist";
		} else if (sorting.equals("pending")) {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = caserv.getPendingCourseApplications();
			if (courseApplications.isEmpty()) {
				String msg = "null";
				model.addAttribute("msg", msg);
			} else {
				model.addAttribute("courseApplications", courseApplications);
				Page<CourseApplication> courseApplicationPage = caserv
						.findPaginatedCourseApplication(PageRequest.of(currentpage - 1, pagesize), courseApplications);
				model.addAttribute("courseApplicationPage", courseApplicationPage);

				int totalPages = courseApplicationPage.getTotalPages();
				if (totalPages > 0) {
					List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
							.collect(Collectors.toList());
					model.addAttribute("pageNumbers", pageNumbers);
				}
				String msg = "found";
				model.addAttribute("msg", msg);
			}
			return "courseapplicationlist";
		} else {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = carepo.findAll();
			if (courseApplications.isEmpty()) {
				String msg = "null";
				model.addAttribute("msg", msg);
			} else {
				model.addAttribute("courseApplications", courseApplications);
				Page<CourseApplication> courseApplicationPage = caserv
						.findPaginatedCourseApplication(PageRequest.of(currentpage - 1, pagesize), courseApplications);
				model.addAttribute("courseApplicationPage", courseApplicationPage);

				int totalPages = courseApplicationPage.getTotalPages();
				if (totalPages > 0) {
					List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
							.collect(Collectors.toList());
					model.addAttribute("pageNumbers", pageNumbers);
				}
				String msg = "found";
				model.addAttribute("msg", msg);
			}
			return "courseapplicationlist";
		}

	}

	@RequestMapping("/viewdepartment")
	public String viewDepartment(Model model, @RequestParam(name = "viewdepartment") Optional<Integer> searchid) {
		int search = searchid.orElse(0);
		model.addAttribute("admin", admin);
		if (search == 0) {
			String msg = "null";
			model.addAttribute("msg", msg);
		} else {
			Department department = drepo.getDepartmentByDepartmentId(search);
			model.addAttribute("department", department);
			String msg = "found";
			model.addAttribute("msg", msg);
			List<Course> courses = crepo.getCoursesByDepartmentId(search);
			model.addAttribute("courses", courses);
			List<Faculty> faculties = frepo.getFacultiesByDepartmentId(search);
			model.addAttribute("faculties", faculties);
		}
		return "viewdepartment";
	}

	@RequestMapping("/viewstudent")
	public String viewStudent(Model model, @RequestParam(name = "viewstudent") Optional<Integer> searchid) {
		int search = searchid.orElse(0);
		model.addAttribute("admin", admin);
		if (search == 0) {
			String msg = "null";
			model.addAttribute("msg", msg);
		} else {
			Student student = srepo.getStudentByStudentId(search);
			model.addAttribute("student", student);
			String msg = "found";
			model.addAttribute("msg", msg);
			List<CourseApplication> courseApplications = carepo.getCourseApplicationsByStudentId(search);
			if (courseApplications.isEmpty()) {
				model.addAttribute("searchca", "null");
			} else {
				model.addAttribute("searchca", "found");
			}
			model.addAttribute("courseApplications", courseApplications);
		}
		return "viewstudent";
	}

	@RequestMapping("/viewfaculty")
	public String viewFaculty(Model model, @RequestParam(name = "viewfaculty") Optional<Integer> searchid) {
		int search = searchid.orElse(0);
		model.addAttribute("admin", admin);
		if (search == 0) {
			String msg = "null";
			model.addAttribute("msg", msg);
		} else {
			Faculty faculty = frepo.getByFacultyId(search);
			model.addAttribute("faculty", faculty);
			String msg = "found";
			model.addAttribute("msg", msg);
			List<Course> courses = crepo.getCoursesByFacultyId(search);
			if (courses.isEmpty()) {
				model.addAttribute("searchcourse", "null");
			} else {
				model.addAttribute("searchcourse", "found");
			}
			model.addAttribute("courses", courses);
		}
		return "viewfaculty";
	}

	@RequestMapping("/viewgrade")
	public String viewGrade(Model model, @RequestParam(name = "viewgrade") Optional<Integer> searchid) {
		int search = searchid.orElse(0);
		model.addAttribute("admin", admin);
		if (search == 0) {
			String msg = "null";
			model.addAttribute("msg", msg);
		} else {
			Grade grade = grepo.getGradeByGradeId(search);
			model.addAttribute("grade", grade);
			String msg = "found";
			model.addAttribute("msg", msg);
		}
		return "viewgrade";
	}

	@RequestMapping("/viewcourse")
	public String viewCourse(Model model, @RequestParam(name = "viewcourse") Optional<Integer> searchid) {
		int search = searchid.orElse(0);
		model.addAttribute("admin", admin);
		if (search == 0) {
			String msg = "null";
			model.addAttribute("msg", msg);
		} else {
			Course course = crepo.getCourseByCourseId(search);
			model.addAttribute("course", course);
			String msg = "found";
			model.addAttribute("msg", msg);
			List<CourseApplication> courseApplications = carepo.getCourseApplicationsByCourseId(search);
			if (courseApplications.isEmpty()) {
				model.addAttribute("searchca", "null");
			} else {
				model.addAttribute("searchca", "found");
			}
			model.addAttribute("courseApplications", courseApplications);
		}
		return "viewcourse";
	}

	@RequestMapping("/viewcourseapplication")
	public String viewCourseApplication(Model model,
			@RequestParam(name = "viewcourseapplication") Optional<Integer> searchid) {
		int search = searchid.orElse(0);
		model.addAttribute("admin", admin);
		if (search == 0) {
			String msg = "null";
			model.addAttribute("msg", msg);
		} else {
			CourseApplication courseApplication = carepo.getCourseApplicationByApplicationId(search);
			model.addAttribute("courseApplication", courseApplication);
			String msg = "found";
			model.addAttribute("msg", msg);

		}
		return "viewcourseapplication";
	}

	@RequestMapping("/delete")
	public String delete(Model model, @RequestParam(name = "deleteType") Optional<String> deleteType,
			@RequestParam(name = "id") Optional<Integer> objectid,
			@RequestParam(name = "confirm") Optional<String> cfm) {
		model.addAttribute("admin", admin);
		String delete = deleteType.orElse("null");
		String confirm = cfm.orElse("no");
		int id = objectid.orElse(0);
		if (delete.equals("null") || id == 0 || confirm.equals("cancel")) {
			return "redirect:/admin/home";
		}
		// for deleting department
		if (delete.equals("department")) {
			Department department = drepo.getDepartmentByDepartmentId(id);
			if (confirm.equals("no")) {
				model.addAttribute("department", department);
				model.addAttribute("type", "department");
				return "confirmdelete";
			} else {
				List<Course> courses = crepo.getCoursesByDepartmentId(id);
				for (Course c : courses) {
					List<CourseApplication> courseApplications = carepo
							.getCourseApplicationsByCourseId(c.getCourseId());
					for (CourseApplication ca : courseApplications) {
						carepo.delete(ca);
					}
					List<Grade> grades = grepo.getGradesByCourseId(c.getCourseId());
					for (Grade g : grades) {
						grepo.delete(g);
					}
					crepo.delete(c);
				}
				List<Faculty> faculties = frepo.getFacultiesByDepartmentId(id);
				for (Faculty f : faculties) {
					List<FacultyLeave> facultyLeaves = flrepo.getFacultyLeaveByFacultyId(f.getFacultyId());
					for (FacultyLeave fl : facultyLeaves) {
						flrepo.delete(fl);
					}
					frepo.delete(f);
				}
				drepo.delete(department);
			}
			return "redirect:/admin/departmentlist";
		}
		// for deleting course
		else if (delete.equals("course")) {
			Course course = crepo.getCourseByCourseId(id);
			Department department = drepo.getDepartmentByDepartmentId(course.getDepartment().getDepartmentId());
			Faculty faculty = frepo.getByFacultyId(course.getFaculty().getFacultyId());
			if (confirm.equals("no")) {
				model.addAttribute("course", course);
				model.addAttribute("department", department);
				model.addAttribute("faculty", faculty);
				model.addAttribute("type", "course");
				return "confirmdelete";
			} else {
				List<CourseApplication> courseApplications = carepo.getCourseApplicationsByCourseId(id);
				for (CourseApplication ca : courseApplications) {
					carepo.delete(ca);
				}
				List<Grade> grades = grepo.getGradesByCourseId(id);
				for (Grade g : grades) {
					grepo.delete(g);
				}
				crepo.delete(course);
			}
			return "redirect:/admin/courselist";
		}

		// for deleting course applications
		else if (delete.equals("courseApplication")) {
			CourseApplication courseApplication = carepo.getCourseApplicationByApplicationId(id);
			if (confirm.equals("no")) {
				model.addAttribute("courseApplication", courseApplication);
				model.addAttribute("type", "courseApplication");
				return "confirmdelete";
			} else {
				carepo.delete(courseApplication);
			}
			return "redirect:/admin/courseapplicationlist";
		}

		// for deleting faculty
		else if (delete.equals("faculty")) {
			Faculty faculty = frepo.getByFacultyId(id);
			List<Course> courses = crepo.getCoursesByFacultyId(id);
			if (confirm.equals("no")) {
				if (courses.isEmpty()) {
					model.addAttribute("deletesafe", "safe");
				} else {
					model.addAttribute("deletesafe", "unsafe");
				}
				model.addAttribute("courses", courses);
				model.addAttribute("faculty", faculty);
				model.addAttribute("type", "faculty");
				return "confirmdelete";
			} else {
				for (Course c : courses) {
					List<CourseApplication> courseApplications = carepo
							.getCourseApplicationsByCourseId(c.getCourseId());
					for (CourseApplication ca : courseApplications) {
						carepo.delete(ca);
					}
					List<Grade> grades = grepo.getGradesByCourseId(c.getCourseId());
					for (Grade g : grades) {
						grepo.delete(g);
					}
					crepo.delete(c);
				}
				frepo.delete(faculty);
			}
			return "redirect:/admin/facultylist";
		}

		// for deleting student
		else if (delete.equals("student")) {
			Student student = srepo.getStudentByStudentId(id);
			List<CourseApplication> courseApplications = carepo.getCourseApplicationsByStudentId(id);
			if (confirm.equals("no")) {
				if (courseApplications.isEmpty()) {
					model.addAttribute("deletesafe", "safe");
				} else {
					model.addAttribute("deletesafe", "unsafe");
				}
				model.addAttribute("student", student);
				model.addAttribute("courseApplications", courseApplications);
				model.addAttribute("type", "student");
				return "confirmdelete";
			} else {
				List<Grade> grades = grepo.getGradesByStudentId(id);
				for (Grade g : grades) {
					grepo.delete(g);
				}
				for (CourseApplication ca : courseApplications) {
					carepo.delete(ca);
				}
				srepo.delete(student);
			}
			return "redirect:/admin/studentlist";
		}

		// for deleting grade
		else if (delete.equals("grade")) {
			Grade grade = grepo.getGradeByGradeId(id);
			if (confirm.equals("no")) {
				model.addAttribute("grade", grade);
				model.addAttribute("type", "grade");
				return "confirmdelete";
			} else {
				grepo.delete(grade);
			}
			return "redirect:/admin/gradelist";
		}
		return "redirect:/admin/home";
	}

	@RequestMapping("/updatedepartment")
	public String updatedepartment(Model model, @RequestParam(name = "id") Optional<Integer> objectid,
			@RequestParam(name = "confirm") Optional<String> cfm,
			@RequestParam(name = "updatename") Optional<String> uname) {
		model.addAttribute("admin", admin);
		String confirm = cfm.orElse("no");
		int id = objectid.orElse(0);

		if (id == 0 || confirm.equals("cancel")) {
			return "redirect:/admin/home";
		}
		// for updating department
		Department department = drepo.getDepartmentByDepartmentId(id);
		if (confirm.equals("no")) {
			model.addAttribute("department", department);
			return "confirmupdatedepartment";
		} else {
			String updatename = uname.orElse("null");
			department.setDepartmentName(updatename);
			drepo.save(department);
			model.addAttribute("department", department);
			model.addAttribute("msg", "found");
			List<Course> courses = crepo.getCoursesByDepartmentId(department.getDepartmentId());
			model.addAttribute("courses", courses);
			List<Faculty> faculties = frepo.getFacultiesByDepartmentId(department.getDepartmentId());
			model.addAttribute("faculties", faculties);
			
			return "viewdepartment";
		}
	}
	
	@RequestMapping("/updatecourse")
	public String updatecourse(Model model, @RequestParam(name = "id") Optional<Integer> objectid,
			@RequestParam(name = "confirm") Optional<String> cfm,
			@RequestParam(name = "updatecode") Optional<String> ucode,
			@RequestParam(name = "updatesize") Optional<Integer> usize,
			@RequestParam(name = "updateunit") Optional<Integer> uunit,
			@RequestParam(name = "updatedid") Optional<Integer> udid,
			@RequestParam(name = "updatefid") Optional<Integer> ufid,
			@RequestParam(name = "updatename") Optional<String> uname) {
		model.addAttribute("admin", admin);
		String confirm = cfm.orElse("no");
		int id = objectid.orElse(0);

		if (id == 0 || confirm.equals("cancel")) {
			return "redirect:/admin/home";
		}
		// for updating course
		Course course = crepo.getCourseByCourseId(id);
		if (confirm.equals("no")) {
			model.addAttribute("course", course);
			return "confirmupdatecourse";
		} else {
			String updatecode = ucode.orElse("null");
			int updatesize = usize.orElse(0);
			int updateunit = uunit.orElse(0);
			int updatedid = udid.orElse(0);
			int updatefid = ufid.orElse(0);
			String updatename = uname.orElse("null");
			
			if(updatesize == 0 || updateunit == 0 || updatedid == 0 || updatefid == 0||updatename.equals("null")) {
				return "redirect:/admin/courselist";
			}
			course.setCourseCode(updatecode);
			course.setCourseSize(updatesize);
			course.setCourseUnit(updateunit);
			course.setCourseName(updatename);
			Department department = drepo.getDepartmentByDepartmentId(updatedid);
			course.setDepartment(department);
			Faculty faculty = frepo.getByFacultyId(updatefid);
			course.setFaculty(faculty);
			crepo.save(course);
			model.addAttribute("course", course);
			model.addAttribute("msg", "found");
			List<CourseApplication> courseApplications = carepo.getCourseApplicationsByCourseId(course.getCourseId());
			if (courseApplications.isEmpty()) {
				model.addAttribute("searchca", "null");
			} else {
				model.addAttribute("searchca", "found");
			}
			model.addAttribute("courseApplications", courseApplications);
			return "viewcourse";
		}
		
	}
	
	
}
