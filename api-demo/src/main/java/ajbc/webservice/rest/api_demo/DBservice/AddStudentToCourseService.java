package ajbc.webservice.rest.api_demo.DBservice;

import ajbc.webservice.rest.api_demo.models.Course;

public class AddStudentToCourseService {

	CourseDBService courseDB = new CourseDBService();
	StudentDBService studenDB = new StudentDBService();
	
//	public Course studentToAdd(long courseNum, long studentId) {
//		
//		//adding student to course
//		courseDB.getCourseByNumber(courseNum).addStudent(studenDB.getStudentById(studentId));
//		
//		//adding course to student list and return the course
//		studenDB.getStudentById(studentId).addCourse(courseNum);
//		return courseDB.getCourseByNumber(courseNum);
//	}
 
}
