package exceptions;

/**
 * @author Liran Hadad
 */
public class OutOfBoardException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public OutOfBoardException() {
		super();
		setMessage("Your index is out of board limits!");
	}

	public OutOfBoardException(String message) {
		super();
		this.message = message;
	}

	public OutOfBoardException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
	
	

}
