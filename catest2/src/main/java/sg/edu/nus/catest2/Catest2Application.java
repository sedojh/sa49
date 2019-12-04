package sg.edu.nus.catest2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sg.edu.nus.catest2.model.*;
import sg.edu.nus.catest2.repo.*;


@SpringBootApplication
public class Catest2Application {

	private static final Logger log = LoggerFactory.getLogger(Catest2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Catest2Application.class, args);
	
	}

	@Bean
	public CommandLineRunner dept(FacultyRepository frepo , FacultyLeaveRepository flrepo,
			DepartmentRepository drepo,CourseRepository crepo,StudentRepository srepo,
			CourseApplicationRepository carepo,GradeRepository grepo) {
		return (args) -> {
			
			/*
			Department d1 = new Department(1, "Math");
			drepo.save(d1);
			
			Student s1 = new Student("Edmund", "The Bomb", "Ong", 98765443, "Dover Road", "edmund@gmail.com", "M", 28);
			Student s2 = new Student("Doris", "The Shitz", "Hui", 98765443, "Dover Road", "doris@gmail.com", "F", 25);
			srepo.save(s1);
			srepo.save(s2);

			Faculty f1 = new Faculty(1, "John", "Fucboi", "Tan", "fucboi91@gmail.com", 98765432, d1);
			Faculty f2 = new Faculty(2, "Jane", "Slow", "Lim", "slowjane91@gmail.com", 98765431, d1);
			frepo.save(f1);
			frepo.save(f2);
			
			FacultyLeave fl1 = new FacultyLeave(1, f1, null, null, "Approved");
			FacultyLeave fl2 = new FacultyLeave(2, f1, null, null, "Pending");
			flrepo.save(fl1);
			flrepo.save(fl2);
			
			Course c1 = new Course(1, "MA1505", "Basic Math", null, null, 4, 30, d1,f1);
			Course c2 = new Course(2, "MA1506", "Intermediate Math", null, null, 4, 80, d1,f2);
			crepo.save(c1);
			crepo.save(c2);
			

			CourseApplication ca1 = new CourseApplication(1, s1, c1, "Approved");
			CourseApplication ca2 = new CourseApplication(2, s1, c2, "Pending");
			carepo.save(ca1);
			carepo.save(ca2);
			
			
			CourseApplication ca3 = new CourseApplication(3, s2, c1, "Approved");
			CourseApplication ca4 = new CourseApplication(4, s2, c2, "Pending");
			carepo.save(ca3);
			carepo.save(ca4);
			
			
			Grade g1 = new Grade(1, s1, c1, f1, "A");
			Grade g2 = new Grade(2,s1,c2,f2,"B");
			Grade g3 = new Grade(3, s2, c1, f1, "C");
			Grade g4 = new Grade(4,s2,c2,f2,"A");
			grepo.save(g1);
			grepo.save(g2);
			grepo.save(g3);
			grepo.save(g4);
			*/
		
			
		};

	}
	
}
