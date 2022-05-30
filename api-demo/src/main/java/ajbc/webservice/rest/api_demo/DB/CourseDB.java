package ajbc.webservice.rest.api_demo.DB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ajbc.webservice.rest.api_demo.models.Course;

public class CourseDB {

	private static CourseDB instance = null;
	private Map<Long, Course> courses;

	public static synchronized CourseDB getInstance() {
		if (instance == null)
			instance = new CourseDB();
		return instance;
	}

	private CourseDB() {
		courses = new HashMap<Long, Course>();

		// seeding the db
		seed();
	}

	private void seed() {
		List<Course> courseList = Arrays.asList(new Course(101, "JAVA"), new Course(102, "Calculus"),
				new Course(103, "Pyshics"));

		courses = courseList.stream().collect(Collectors.toMap(Course::getNumber, Function.identity()));
	}

	public Map<Long, Course> getCourses() {
		return courses;
	}

}
