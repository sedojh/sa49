package sg.edu.nus.catest2.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.nus.catest2.model.Faculty;
import sg.edu.nus.catest2.model.FacultyLeave;
import sg.edu.nus.catest2.mvcmodel.Session;
import sg.edu.nus.catest2.service.FacultyLeaveService;
import sg.edu.nus.catest2.service.FacultyService;

@Controller
@RequestMapping("/facultyleave")
@SessionAttributes("usersession")
public class FacultyLeaveController {
	
	@Autowired
	FacultyLeaveService facuLservice;
	@Autowired
	FacultyService fserv;
	
	private static Faculty faculty;
	private FacultyLeave facultyleave;
	
	
	@GetMapping("/leaveList")
	public String facultyLeaveRecord(Model model,
									@RequestParam(name = "sort") Optional<String> sort,
									@SessionAttribute("usersession") Session session) {
		
		System.out.println("Faculty Leave Record:");	
		String sorting = sort.orElse("all");
		
		if(sorting.equals("approved")) {
					
					System.out.print("CHECK SORT:"+sorting);
					model.addAttribute("sort", sorting);
					
					ArrayList<FacultyLeave> fLeaveList = facuLservice.getApprovedFacultyLeaveByFacultyId(session.getSessionId());
					System.out.println("**Reterive facultyLeaveList**:"+fLeaveList);
					model.addAttribute("faculty", faculty);
					model.addAttribute("fLeaveList",fLeaveList);					
						
					return "facultyLeaveRec";
			}else if(sorting.equals("pending")) 
			{
			
				System.out.print("CHECK SORT:"+sorting);
				model.addAttribute("sort", sorting);
				
				ArrayList<FacultyLeave> fLeaveList = facuLservice.getPendingFacultyLeaveByFacultyId(session.getSessionId());
				System.out.println("**Reterive facultyLeaveList**:"+fLeaveList);
				model.addAttribute("faculty", faculty);
				model.addAttribute("fLeaveList",fLeaveList);					
					
				return "facultyLeaveRec";
			}else if(sorting.equals("rejected")) 
			{
			
				System.out.print("CHECK SORT:"+sorting);
				model.addAttribute("sort", sorting);
				
				ArrayList<FacultyLeave> fLeaveList = facuLservice.getRejectedFacultyLeaveByFacultyId(session.getSessionId());
				System.out.println("**Reterive facultyLeaveList**:"+fLeaveList);
				model.addAttribute("faculty", faculty);
				model.addAttribute("fLeaveList",fLeaveList);					
					
				return "facultyLeaveRec";
			}else {
				System.out.print("CHECK SORT:"+sorting);
				model.addAttribute("sort", sorting);
				
				ArrayList<FacultyLeave> fLeaveList = facuLservice.getFacultyLeaveByFacultyId(session.getSessionId());
				System.out.println("**Reterive facultyLeaveList**:"+fLeaveList);
				model.addAttribute("faculty", faculty);
				model.addAttribute("fLeaveList",fLeaveList);					
					
				return "facultyLeaveRec";
			}
					
		
	}
	@RequestMapping("/applyLeaveForm")
	public String applyLeaveForm(Model model,@SessionAttribute("usersession") Session session) {
		
		System.out.println("Faculty Apply Leave Form:");
		model.addAttribute("facultyId",session.getSessionId());
		model.addAttribute("facultyleave", facultyleave);				
		return "facultyApplyLeaveForm";
	}
	@PostMapping("/applyLeave")
	public String applyLeave(	Model model,
								@RequestParam("start") Optional<String> start, 
								@RequestParam("end") Optional<String> end,
								@SessionAttribute("usersession") Session session) {
		
		System.out.println("Faculty Apply Leave:");
		
		Faculty f=fserv.getFacultyByFacultyId(session.getSessionId());
		FacultyLeave fl=new FacultyLeave();
		String startD = start.orElse("null");
		String endD = end.orElse("null");
		
		if(startD.isBlank()||endD.isBlank()) {
			model.addAttribute("facultyId",session.getSessionId());
			model.addAttribute("error", "error");
			model.addAttribute("msg", "Date are not Selected.");
			return "facultyApplyLeaveForm";
		}
		
		LocalDate dstart = LocalDate.parse(startD);
		LocalDate dend = LocalDate.parse(endD);

		System.out.println("startD:"+dstart+"\nEndD"+dend);
		
		if (dend.isBefore(dstart)) {
			model.addAttribute("error", "error");
			model.addAttribute("msg", "End Date cannot be earlier than Start Date!");
			model.addAttribute("facultyId",session.getSessionId());
			
			return "facultyApplyLeaveForm";
		}
		fl.setFaculty(f);
		fl.setLeaveStart(dstart.plusDays(1));
		fl.setLeaveEnd(dend.plusDays(1));
		fl.setStatus("Pending");
		facuLservice.save(fl);
		return "redirect:/facultyleave/leaveList";
	}

	@GetMapping("/deleteLeave/{flId}")
	public String deleteCoures(	@PathVariable("flId") int flId,
								@SessionAttribute("usersession") Session session) {
		
		facuLservice.deleteById(flId);
		
		return "redirect:/facultyleave/leaveList";
	}

}
