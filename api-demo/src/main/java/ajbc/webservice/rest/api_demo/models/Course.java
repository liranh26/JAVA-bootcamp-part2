package ajbc.webservice.rest.api_demo.models;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private static Long counter = 100l;
	private Long courseNumber;
	private String name;
	private List<Student> courseStudents;
	
	public Course() {
		setCourseNumber(counter++);
		courseStudents = new ArrayList<Student>();
	}
	
	public Course(String name) {
		courseStudents = new ArrayList<Student>();
		setCourseNumber(counter++);
		setName(name);
	}

	public long getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(long number) {
		this.courseNumber = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Student addStudent(Student student) {
		courseStudents.add(student);
		return student;
	}

	@Override
	public String toString() {
		return "Course [number=" + courseNumber + ", name=" + name + ", courseStudents=" + courseStudents + "]";
	}
	
}
