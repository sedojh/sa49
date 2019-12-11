package sg.edu.nus.catest2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sg.edu.nus.catest2.repo.*;
import sg.edu.nus.catest2.model.*;

@Service
public class CourseApplicationService {
	@Autowired
	CourseApplicationRepository carepo;
	
	public List<CourseApplication> getPendingCourseApplications(){
		List<CourseApplication> list = carepo.findAll();
		List<CourseApplication> courseApplications = new ArrayList<CourseApplication>();
		for(CourseApplication ca: list) {
			if(ca.getStatus().equals("Pending")) {
				courseApplications.add(ca);
			}
		}
		return courseApplications;
	}
	
	public List<CourseApplication> getApprovedCourseApplications(){
		List<CourseApplication> list = carepo.findAll();
		List<CourseApplication> courseApplications = new ArrayList<CourseApplication>();
		for(CourseApplication ca: list) {
			if(ca.getStatus().equals("Approved")) {
				courseApplications.add(ca);
			}
		}
		return courseApplications;
	}
	
	public List<CourseApplication> getRejectedCourseApplications(){
		List<CourseApplication> list = carepo.findAll();
		List<CourseApplication> courseApplications = new ArrayList<CourseApplication>();
		for(CourseApplication ca: list) {
			if(ca.getStatus().equals("Rejected")) {
				courseApplications.add(ca);
			}
		}
		return courseApplications;
	}
	
	public Page<CourseApplication> findPaginatedCourseApplication(Pageable pageable,
			List<CourseApplication> courseApplications) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<CourseApplication> list;

		if (courseApplications.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, courseApplications.size());
			list = courseApplications.subList(startItem, toIndex);
		}

		Page<CourseApplication> courseApplicationPage = new PageImpl<CourseApplication>(list, PageRequest.of(currentPage, pageSize),
				courseApplications.size());

		return courseApplicationPage;
	}

}
