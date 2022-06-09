package ajbc.webservice.rest.api_demo.exceptions;

import ajbc.webservice.rest.api_demo.models.ErrorMessage;
import ajbc.webservice.rest.api_demo.models.InternalErrorCode;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MissingDataExceptionMapper implements ExceptionMapper<MissingDataException> {

	@Override
	public Response toResponse(MissingDataException ex) {
		ErrorMessage errorMsg = new ErrorMessage(ex.getMessage(), "www.ask.guy.com", InternalErrorCode.INVALID_ID);
//		return Response.status(InternalErrorCode.INVALID_ID.getCodeNum()).entity(errorMsg).build();
		return Response.status(Status.NOT_FOUND).entity(errorMsg).build();

	}
	
}
