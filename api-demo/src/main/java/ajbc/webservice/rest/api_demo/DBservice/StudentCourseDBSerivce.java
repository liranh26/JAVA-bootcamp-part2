package ajbc.webservice.rest.api_demo.DBservice;

import java.util.List;
import java.util.Map;

import ajbc.webservice.rest.api_demo.DB.MyDB;
import ajbc.webservice.rest.api_demo.exceptions.MissingDataException;
import ajbc.webservice.rest.api_demo.models.Course;
import ajbc.webservice.rest.api_demo.models.Student;

public class StudentCourseDBSerivce {

	private MyDB db;
	private Map<Long, Student> students;
	private Map<Long, Course> courses;
	
//	public StudentCourseDBSerivce() {
//		DB = My
//	}
	
	public List<Course> getCousesByStudentId(long id){
		
		List<Course> courses = null;
		Student student = students.get(id);
		if(student == null)
			throw new MissingDataException("Student with id %d is not in DB".formatted(id));
		courses = student.getCourses();
		
		return courses;
	}
	
	

}
