package ajbc.webservice.rest.api_demo.models;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private long number;
	private String name;
	private List<Student> courseStudents;
	
	public Course() {
		courseStudents = new ArrayList<Student>();
	}
	
	public Course(long number, String name) {
		courseStudents = new ArrayList<Student>();
		setNumber(number);
		setName(name);
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
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
		return "Course [number=" + number + ", name=" + name + ", courseStudents=" + courseStudents + "]";
	}
	
}
