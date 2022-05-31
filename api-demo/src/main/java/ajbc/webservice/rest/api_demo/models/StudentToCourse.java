package ajbc.webservice.rest.api_demo.models;

public class StudentToCourse {

	private long studentId;
	private long courseNum;
	

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(long courseNum) {
		this.courseNum = courseNum;
	}

	@Override
	public String toString() {
		return "StudentToCourse Adding student id=" + studentId + " to course number=" + courseNum + "]";
	}
	
	
	
}
