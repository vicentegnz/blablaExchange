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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import model.Comment;
import model.User;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;

@Path("/comments")
public class CommentsResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getCommentsJSON(@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);

		List<Comment> comments = commentDAO.getAll();

		return comments;

	}

	@GET
	@Path("/{idc: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getCommentJSON(@PathParam("idc") long commentid, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);

		Comment comment = commentDAO.get(commentid);

		if (comment != null)
			return comment;
		else
			throw new CustomNotFoundException("Order (" + commentid + ") is not found");

	}

	@GET
	@Path("/sender")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getCommentBySenderJSON(@QueryParam("senderid") long senderid,
			@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);

		List<Comment> comments = commentDAO.getAllBySender(senderid);

		if (comments != null)
			return comments;
		else
			throw new CustomNotFoundException("Sender  (" + senderid + ") is not found");

	}

	@GET
	@Path("/receiver")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getCommentByReceiverJSON(@QueryParam("receiverid") long receiverid,
			@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);

		List<Comment> comments = commentDAO.getAllByReceiver(receiverid);

		if (comments != null)
			return comments;
		else
			throw new CustomNotFoundException("Receiver  (" + receiverid + ") is not found");

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Comment newComment, @Context HttpServletRequest request) throws Exception {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);

		Response res;

		List<String> messages = new ArrayList<String>();
		if (!newComment.validate(messages))
			throw new CustomBadRequestException(messages);
		long id = commentDAO.add(newComment);

		res = Response // return 201 and Location: /orders/newid
				.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

		return res;
	}

	// POST que recibe datos del nuevo usuario por formulario
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response post(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDAO = new JDBCCommentDAOImpl();

		HttpSession session = request.getSession();
		User userProfile = (User) session.getAttribute("user");

		commentDAO.setConnection(conn);

		Response res;
		List<String> messages = new ArrayList<>();

		Integer idu = Integer.parseInt(formParams.getFirst("idu"));

		Comment newComment = new Comment();

		newComment.setReceiver(idu);
		newComment.setSender(userProfile.getIdu());
		newComment.setText(formParams.getFirst("comentario"));

		if (!newComment.validate(messages))
			throw new CustomBadRequestException(messages);
		// save order in DB
		long id = commentDAO.add(newComment);

		res = Response // return 201 and Location: /orders/newid
				.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

		return res;
	}

	// PUT que actualiza a partir del objeto recibido
	@PUT
	@Path("/{idc: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(Comment commentUpdate, @PathParam("idc") long commentid, @Context HttpServletRequest request)
			throws Exception {
		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDAO = new JDBCCommentDAOImpl();

		HttpSession session = request.getSession();
		User userProfile = (User) session.getAttribute("user");

		commentDAO.setConnection(conn);
		List<String> messages = new ArrayList<>();

		// Comprobamos que existe el usuario
		Comment comment = commentDAO.get(commentid);

		if ((comment != null) && (comment.getSender() == userProfile.getIdu())) {
			if (comment.getIdc() != commentid) {
				messages.add("Error in id");
				throw new CustomBadRequestException(messages);
			} else {
				if (commentUpdate.validate(messages)) {
					commentDAO.save(commentUpdate);
				} else {
					throw new CustomBadRequestException(messages);
				}
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return response;
	}

	@DELETE
	@Path("/{idc: [0-9]+}")
	public Response deleteComment(@PathParam("idc") long idc, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User userProfile = (User) session.getAttribute("user");

		Comment comment = commentDAO.get(idc);
		List<String> messages = new ArrayList<>();

		if ((comment != null) && (userProfile.getIdu() == comment.getSender())) {
			commentDAO.delete(idc);
			comment = null;
			return Response.noContent().build(); // 204 no content
		} else {
			messages.add("Error in id Comment");
			throw new CustomBadRequestException(messages);
		}

	}
}