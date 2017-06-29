package resources.exceptions;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CustomBadRequestException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -402752772426499120L;

	public CustomBadRequestException() {
		super(Response.status(Response.Status.BAD_REQUEST).build());

	}

	public CustomBadRequestException(List<String> messages) {
		super(Response.status(Response.Status.BAD_REQUEST)
				.entity("{\"status\" : \"404\", \"userMessage\" : \"" + convertArrayToString(messages) + "\"}")
				.type("application/json").build());
	}

	public static String convertArrayToString(List<String> messages) {
		String message = "";
		for (int i = 0; i < messages.size(); i++) {
			message = message + messages.get(i);
		}
		return message;
	}
}
