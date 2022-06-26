package ajbc.learn.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"picture"})
@Entity
@Table(name="categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //connecting hibernate to know id is identity
	private int categoryId;
	@Column(name="categoryName") //if the name in the db is diffrenet then the field 
	private String catName;
	private String description;
	private byte[] picture;
}
