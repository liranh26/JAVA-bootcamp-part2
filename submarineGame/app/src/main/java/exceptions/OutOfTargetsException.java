package exceptions;

/**
 * @author Liran Hadad
 */
public class OutOfTargetsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public OutOfTargetsException() {
		super();
		message = "All subs have been found! well done!";
	}

	public OutOfTargetsException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
