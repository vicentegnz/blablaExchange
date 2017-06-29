package resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.JDBCUsers_LanguagesDAOImpl;
import dao.Users_LanguagesDAO;
import model.User;
import model.Users_Languages;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;

@Path("/usersLanguages")
public class UsersLanguagesResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users_Languages> getUsers_LanguagesJSON(@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		Users_LanguagesDAO userLanguagesDAO = new JDBCUsers_LanguagesDAOImpl();
		userLanguagesDAO.setConnection(conn);

		return userLanguagesDAO.getAll();

	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users_Languages> getUsersLanguagesbyUserJSON(@QueryParam("userid") long userid,
			@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		Users_LanguagesDAO userLanguagesDAO = new JDBCUsers_LanguagesDAOImpl();
		userLanguagesDAO.setConnection(conn);

		List<Users_Languages> userL = userLanguagesDAO.getAllByUser(userid);

		if (userL != null)
			return userL;
		else
			throw new CustomNotFoundException(" User (" + userid + ") is not found");

	}

	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users_Languages> getUsersLanguagesbyUserbyLevebyLanguageJSON(@QueryParam("level") String level,
			@QueryParam("idl") long idl, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		Users_LanguagesDAO userLanguagesDAO = new JDBCUsers_LanguagesDAOImpl();
		userLanguagesDAO.setConnection(conn);
		List<Users_Languages> users_LanguagesList;
		if (level != null) {
			users_LanguagesList = userLanguagesDAO.getAllByLanguage(idl);
			int i = 0;
			while (i < users_LanguagesList.size()) {
				if (users_LanguagesList.get(i).getLevel().compareTo(level) < 0) {
					users_LanguagesList.remove(i);
				}
				i++;
			}
		} else {
			users_LanguagesList = userLanguagesDAO.getAll();
		}

		if (users_LanguagesList != null)
			return users_LanguagesList;
		else
			throw new CustomNotFoundException(" User (" + level + ") is not found");

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Users_Languages newUser_Languages, @Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null)
			System.out.println("usuario: " + user.getUsername());

		Response res = null;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		Users_LanguagesDAO userLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
		userLanguagesDao.setConnection(conn);

		List<String> messages = new ArrayList<String>();
		if (!newUser_Languages.validate(messages))
			throw new CustomBadRequestException(messages);
		// save order in DB
		userLanguagesDao.add(newUser_Languages);

		return res;
	}

	// PUT que actualiza a partir del objeto recibido
	@PUT
	@Path("/{idl: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(Users_Languages userLanguageUpdate, @PathParam("idl") long userLanguageidl,
			@Context HttpServletRequest request) throws Exception {
		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");

		Users_LanguagesDAO userLanguagesDAO = new JDBCUsers_LanguagesDAOImpl();
		userLanguagesDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Users_Languages usl = userLanguagesDAO.get(user.getIdu(), userLanguageidl);

		List<String> messages = new ArrayList<>();
		if (usl.getIdu() == userLanguageUpdate.getIdu()) {
			// Comprobamos que existe el usuario
			if (userLanguageUpdate.validate(messages))
				userLanguagesDAO.save(userLanguageUpdate);
			else
				throw new CustomBadRequestException(messages);
		} else {
			messages.add("Errors usl not exist");
			throw new CustomBadRequestException(messages);
		}
		return response;
	}

	@DELETE
	@Path("/{idl: [0-9]+}")
	public Response deleteUser_Languages(@PathParam("idl") long idl, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		Users_LanguagesDAO userLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
		userLanguagesDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<String> messages = new ArrayList<>();

		if (user != null) {
			System.out.println("IdUsuario: " + user.getIdu() + " IdIdioma: " + idl);
			userLanguagesDao.delete(user.getIdu(), idl);
			return Response.noContent().build(); // 204 no content
		} else {
			messages.add("Error in UserLanguageID");
			throw new CustomBadRequestException(messages);
		}

	}

}
