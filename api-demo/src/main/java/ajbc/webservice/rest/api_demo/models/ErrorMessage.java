package ajbc.webservice.rest.api_demo.models;

public class ErrorMessage {
	
	private String message;
	private String linkToDocs;
	private InternalErrorCode error;
	
	public ErrorMessage() {}
	
	public ErrorMessage(String message, String linkToDocs, InternalErrorCode error) {
		this.message = message;
		this.linkToDocs = linkToDocs;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLinkToDocs() {
		return linkToDocs;
	}

	public void setLinkToDocs(String linkToDocs) {
		this.linkToDocs = linkToDocs;
	}

	public InternalErrorCode getError() {
		return error;
	}

	public void setError(InternalErrorCode error) {
		this.error = error;
	}
	
}
