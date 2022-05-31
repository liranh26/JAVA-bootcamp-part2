package ajbc.webservice.rest.api_demo.rescources;

import ajbc.webservice.rest.api_demo.DBservice.AddStudentToCourseService;
import ajbc.webservice.rest.api_demo.models.Course;
import ajbc.webservice.rest.api_demo.models.StudentToCourse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;

//@Path("/add-student-to-course")
@Path("/add")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class AddStudentToCourseResource {
	
	AddStudentToCourseService addingStudingService = new AddStudentToCourseService();
	
	@POST
	public Course addStudentToCourse(StudentToCourse studentToCourse){
		return addingStudingService.studentToAdd(studentToCourse.getCourseNum(), studentToCourse.getStudentId());
	}
	 
	
}
