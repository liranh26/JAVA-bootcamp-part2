package ajbc.learn.mongodb.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Movies {

	private ObjectId id;
	private int year;
	private List<String> cast;
	private String title;
	@BsonProperty(value="num_mflix_comments") 
	private int comments;
	
	public Movies(ObjectId id, int year, List<String> cast, String title, int comments) {
		this.id = id;
		this.year = year;
		this.cast = cast;
		this.title = title;
		this.comments = comments;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<String> getCast() {
		return cast;
	}

	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Movies [id=" + id + ", year=" + year + ", cast=" + cast + ", title=" + title + ", comments=" + comments
				+ "]";
	}
	
}
