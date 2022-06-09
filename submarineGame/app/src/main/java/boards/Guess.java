package boards;

import java.io.Serializable;

/**
*@author Liran Hadad
*/
public class Guess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xCord;
	private int yCord;
	
	public Guess() {
		initializeGuess();
	}
	
	public Guess(int x, int y) {
		xCord = x;
		yCord = y;
	}

	public int getxCord() {
		return xCord;
	}

	public void xRecordCoord(int xCord) {
		this.xCord = xCord;
	}

	public int getyCord() {
		return yCord;
	}

	public void yRecordCoord(int yCord) {
		this.yCord = yCord;
	}
	
	public void initializeGuess() {
		xRecordCoord(0);
		yRecordCoord(0);
	}

	@Override
	public String toString() {
		return "Guess [x Coordinate=" + xCord + ", y Coordinate=" + yCord + "]";
	}
	
	
}
