package sg.edu.nus.catest2.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.catest2.repo.*;
import sg.edu.nus.catest2.model.*;
import org.springframework.web.servlet.ModelAndView;
import sg.edu.nus.catest2.service.*;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	DepartmentRepository drepo;
	@Autowired
	private DepartmentService dservice;
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String all(Model model,@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size) {
		
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		
		Page<Department> departmentPage = dservice.findPaginatedDepartment(PageRequest.of(currentpage - 1, pagesize));
		 
        model.addAttribute("departmentPage", departmentPage);
 
        int totalPages = departmentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
 
        return "alldept";
		
		
	}

}
