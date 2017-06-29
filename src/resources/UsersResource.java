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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;
import util.EncryptedAlgorithm;

@Path("/users")
public class UsersResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersJSON(@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		List<User> users = userDAO.getAll();

		return users;

	}

	@GET
	@Path("/{idu: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserbyIdJSON(@PathParam("idu") long userid, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		User user = userDAO.get(userid);

		if (user != null)
			return user;
		else
			throw new CustomNotFoundException("User (" + userid + ") is not found");

	}

	@GET
	@Path("/current")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		User user2 = userDAO.get(user.getIdu());
		return user2;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(User newUser, @Context HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null)
			System.out.println("usuario: " + user.getUsername());

		Response res;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		String passwordEncrypted = EncryptedAlgorithm.getHash(newUser.getPassword());

		List<String> messages = new ArrayList<String>();
		if (!newUser.validate(messages)) {
			throw new CustomBadRequestException(messages);

		} else {
			newUser.setPassword(passwordEncrypted);
			long id = userDao.add(newUser);
			res = Response.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
		}
		return res;
	}

	@PUT
	@Path("/{idu: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(User userUpdate, @PathParam("idu") long userid, @Context HttpServletRequest request)
			throws Exception {
		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		List<String> messages = new ArrayList<>();

		// Comprobamos que existe el usuario
		User user = userDAO.get(userUpdate.getIdu());
		String passwordEncrypted = EncryptedAlgorithm.getHash(userUpdate.getPassword());

		if (user != null) {
			if (user.getIdu() != userid) {
				messages.add("Error in id");
				throw new CustomBadRequestException(messages);
			} else {
				if (userUpdate.validate(messages)) {
					userUpdate.setPassword(passwordEncrypted);
					userDAO.save(userUpdate);
				} else {
					throw new CustomBadRequestException(messages);
				}
			}
		}
		return response;
	}

	@DELETE
	@Path("/{idu: [0-9]+}")
	public Response deleteUser(@PathParam("idu") long idu, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<String> messages = new ArrayList<>();

		if (user != null) {
			userDao.delete(user.getIdu());
			user = null;
			return Response.noContent().build(); // 204 no content
		} else {
			messages.add("Error in user or id");
			throw new CustomBadRequestException(messages);
		}

	}
}
