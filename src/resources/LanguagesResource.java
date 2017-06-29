package resources;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import dao.JDBCLanguageDAOImpl;
import dao.LanguageDAO;
import model.Language;
import resources.exceptions.CustomNotFoundException;

@Path("/languages")
public class LanguagesResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Language> getLanguagesJSON(@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		List<Language> language = languageDAO.getAll();

		return language;

	}

	@GET
	@Path("/{idl: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Language getLanguagesJSON(@PathParam("idl") long languageid, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		Language language = languageDAO.get(languageid);

		if (language != null)
			return language;
		else
			throw new CustomNotFoundException("Order (" + languageid + ") is not found");

	}

}