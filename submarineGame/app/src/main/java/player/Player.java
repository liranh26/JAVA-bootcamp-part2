package player;

import java.io.Serializable;

/**
*@author Liran Hadad
*/
public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String name;
	protected String email;
	protected String phone;
	
	public Player(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Player name=" + name + ", email=" + email + ", phone=" + phone + "";
	}
	
	
}
