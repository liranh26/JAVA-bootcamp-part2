package ajbc.webservice.rest.api_demo.rescources;

import java.util.List;

import ajbc.webservice.rest.api_demo.DBservice.CourseDBService;
import ajbc.webservice.rest.api_demo.models.Course;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public class CourseResource {

	CourseDBService studentDB = new CourseDBService();
	
	//GET - all courses in DB
	@GET
	@Path("/courses")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAllCourses() {
		return studentDB.getAllCourses();
	}
	
	@GET
	@Path("/courses/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourseByNumber(@PathParam("number")int courseNum) {
		return studentDB.getCourseByNumber(courseNum);
	}
	
	//POST - add new student
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Course> addStudent(Course course) {
			return studentDB.addCourse(course);
		}
	
}
