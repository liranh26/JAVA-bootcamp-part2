package ajbc.learn.mongodb.models;

import java.util.List;

import org.bson.types.ObjectId;

public class Student {

	private ObjectId id;
	private int studentId;
	private String firstName;
	private int classId;
	private String lastName;
	private List<Exam> exams;

	public Student(ObjectId id, int studentId, int classId, String firstName, String lastName, List<Exam> exams) {
		this.id = id;
		this.studentId = studentId;
		this.firstName = firstName;
		this.classId = classId;
		this.lastName = lastName;
		this.exams = exams;
	}

	public Student(int studentId, int classId, String firstName, String lastName, List<Exam> exams) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.classId = classId;
		this.lastName = lastName;
		this.exams = exams;
	}

	public Student() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", studentId=" + studentId + ", firstName=" + firstName + ", classId=" + classId
				+ ", lastName=" + lastName + ", exams=" + exams + "]";
	}
}