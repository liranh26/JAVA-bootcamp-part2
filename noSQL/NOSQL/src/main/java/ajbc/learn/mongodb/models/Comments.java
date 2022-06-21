package ajbc.learn.mongodb.models;

import java.time.LocalDateTime;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Comments {

	
	private ObjectId id;
	private String name;
	private String email;
	@BsonProperty(value="movie_id") 
	private ObjectId movieId;
	private String text;
	private LocalDateTime date;

	public Comments(ObjectId commentId, String name, String email, ObjectId movieId, String text, LocalDateTime date) {
		this.id = commentId;
		this.name = name;
		this.email = email;
		this.movieId = movieId;
		this.text = text;
		this.date = date;
	}

	public ObjectId getCommentId() {
		return id;
	}

	public void setCommentId(ObjectId commentId) {
		this.id = commentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ObjectId getMovieId() {
		return movieId;
	}

	public void setMovieId(ObjectId movieId) {
		this.movieId = movieId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}



	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Comments [commentId=" + id + ", name=" + name + ", email=" + email + ", movieId=" + movieId
				+ ", text=" + text + ", date=" + date + "]";
	}
	
}
