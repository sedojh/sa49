package sg.edu.nus.catest2.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.nus.catest2.model.Faculty;
import sg.edu.nus.catest2.repo.FacultyRepository;

@Service
public class FacultyService {
	@Autowired
	FacultyRepository frepo;
	
	public Page<Faculty> findPaginatedFaculty(Pageable pageable) {
		List<Faculty> faculties = frepo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Faculty> list;

		if (faculties.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, faculties.size());
			list = faculties.subList(startItem, toIndex);
		}

		Page<Faculty> facultyPage = new PageImpl<Faculty>(list, PageRequest.of(currentPage, pageSize),
				faculties.size());

		return facultyPage;
	}

}
