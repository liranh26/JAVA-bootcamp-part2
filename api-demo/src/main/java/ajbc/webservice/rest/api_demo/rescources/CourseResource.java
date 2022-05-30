package ajbc.webservice.rest.api_demo.rescources;

import java.util.List;


import ajbc.webservice.rest.api_demo.DBservice.CourseDBService;
import ajbc.webservice.rest.api_demo.models.Course;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CourseResource {

	CourseDBService coursesDB = new CourseDBService();

	@GET
	public List<Course> getAllCourses() {
		return coursesDB.getAllCourses();
	}

	@GET
	@Path("/{number}")
	public Course getCourseByNumber(@PathParam("number") long courseNum) {
		return coursesDB.getCourseByNumber(courseNum);
	}

	
	@POST
	public Course addCourse(Course course) {
		return coursesDB.addCourse(course);
	}

	@PUT
	@Path("/{number}")
	public Course updateCourse(@PathParam("number") long courseNum, Course course) {
		return coursesDB.updateCourse(course, courseNum);
	}
	
	@DELETE
	@Path("/{number}")
	public Course removeCourse(@PathParam("number") long courseNum) {
		return coursesDB.reomveCourse(courseNum);
	}
	
}
