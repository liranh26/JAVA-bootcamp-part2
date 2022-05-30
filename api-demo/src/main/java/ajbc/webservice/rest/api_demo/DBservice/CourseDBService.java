package ajbc.webservice.rest.api_demo.DBservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ajbc.webservice.rest.api_demo.DB.CourseDB;
import ajbc.webservice.rest.api_demo.DB.MyDB;
import ajbc.webservice.rest.api_demo.models.Course;
import ajbc.webservice.rest.api_demo.models.Student;

public class CourseDBService {

	private CourseDB db;
	private Map<Long, Course> courses;

	public CourseDBService() {
		db = CourseDB.getInstance();
		courses = db.getCourses();
	}

	public List<Course> getAllCourses() {
		return new ArrayList<Course>(courses.values());
	}

	public Course getCourseByNumber(int courseNum) {
		for (Course course : courses) {
			if (course.getNumber() == courseNum)
				return course;
		}
		return null;
	}

	public List<Course> addCourse(Course course) {
		courses.add(course);
		return courses;
	}

}
