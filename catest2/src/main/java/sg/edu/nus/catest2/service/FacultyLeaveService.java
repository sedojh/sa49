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

import sg.edu.nus.catest2.model.FacultyLeave;
import sg.edu.nus.catest2.repo.FacultyLeaveRepository;

@Service
public class FacultyLeaveService {
	
	@Autowired
	FacultyLeaveRepository flrepo;
	
	public List<FacultyLeave> getPendingFacultyLeaves(){
		List<FacultyLeave> list = flrepo.findAll();
		List<FacultyLeave> facultyLeaves = new ArrayList<FacultyLeave>();
		for(FacultyLeave fl: list) {
			if(fl.getStatus().equals("Pending")) {
				facultyLeaves.add(fl);
			}
		}
		return facultyLeaves;
	}
	
	public List<FacultyLeave> getApprovedFacultyLeaves(){
		List<FacultyLeave> list = flrepo.findAll();
		List<FacultyLeave> facultyLeaves = new ArrayList<FacultyLeave>();
		for(FacultyLeave fl: list) {
			if(fl.getStatus().equals("Approved")) {
				facultyLeaves.add(fl);
			}
		}
		return facultyLeaves;
	}
	
	public List<FacultyLeave> getRejectedFacultyLeaves(){
		List<FacultyLeave> list = flrepo.findAll();
		List<FacultyLeave> facultyLeaves = new ArrayList<FacultyLeave>();
		for(FacultyLeave fl: list) {
			if(fl.getStatus().equals("Rejected")) {
				facultyLeaves.add(fl);
			}
		}
		return facultyLeaves;
	}
	
	public Page<FacultyLeave> findPaginatedFacultyLeave(Pageable pageable,
			List<FacultyLeave> facultyLeaves) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<FacultyLeave> list;

		if (facultyLeaves.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, facultyLeaves.size());
			list = facultyLeaves.subList(startItem, toIndex);
		}

		Page<FacultyLeave> facultyLeavePage = new PageImpl<FacultyLeave>(list, PageRequest.of(currentPage, pageSize),
				facultyLeaves.size());

		return facultyLeavePage;
	}

}
