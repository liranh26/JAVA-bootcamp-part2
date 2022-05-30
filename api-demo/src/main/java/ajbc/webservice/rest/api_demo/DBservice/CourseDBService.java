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

	public Course getCourseByNumber(long courseNum) {
		return courses.get(courseNum);
	}

	public Course addCourse(Course course) {
		if(course == null)
			return null;
		courses.put(course.getCourseNumber(), course);
		return course;
	}
	
	public Course updateCourse(Course course, long courseNumber) {
		if(!courses.containsKey(courseNumber))
			return null;
		course.setCourseNumber(courseNumber);
		courses.put(courseNumber, course);
		return course;
	}
	
	public Course reomveCourse(long courseNum) {
		return courses.remove(courseNum);
	}

}
