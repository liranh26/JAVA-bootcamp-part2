package ajbc.webservice.rest.api_demo.exceptions;

public class MissingDataException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public MissingDataException(String msg) {
		super(msg);
	}
	
}
