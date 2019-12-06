package sg.edu.nus.catest2.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.nus.catest2.model.Student;
import sg.edu.nus.catest2.repo.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository srepo;
	
	public Page<Student> findPaginatedStudent(Pageable pageable) {
		List<Student> students = srepo.findAll();
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Student> list;

		if (students.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, students.size());
			list = students.subList(startItem, toIndex);
		}

		Page<Student> studentPage = new PageImpl<Student>(list, PageRequest.of(currentPage, pageSize),
				students.size());

		return studentPage;
	}

}
