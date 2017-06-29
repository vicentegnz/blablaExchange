package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCLanguageDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
import model.Comment;
import model.Language;
import model.User;
import model.Users_Languages;
import util.Triplet;

/**
 * Servlet implementation class EditCommentUserServlet
 */
@WebServlet(urlPatterns = { "/users/EditCommentUserServlet" })
public class EditCommentUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String convertTime(long time) {

		Date date = new Date(time);

		Format format = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

		return format.format(date);

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditCommentUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		Users_LanguagesDAO users_LanguagesDAO = new JDBCUsers_LanguagesDAOImpl();
		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);
		userDAO.setConnection(conn);
		users_LanguagesDAO.setConnection(conn);
		languageDAO.setConnection(conn);
		commentDAO.setConnection(conn);

		String iduS = request.getParameter("idu");
		User userProfile;
		List<Comment> comments;
		if (iduS != null) {
			System.out.println("IDUS no es nulo");
			Integer idu = Integer.parseInt(iduS);
			userProfile = userDAO.get(idu);
			comments = commentDAO.getAllByReceiver(idu);
		} else {
			System.out.println("IDUS es nulo");

			iduS = (String) request.getAttribute("idu");
			Integer idu = Integer.parseInt(iduS);
			userProfile = userDAO.get(idu);
			comments = commentDAO.getAllByReceiver(idu);
		}

		List<Users_Languages> users_LanguagesList = users_LanguagesDAO.getAllByUser(userProfile.getIdu());
		Iterator<Users_Languages> it = users_LanguagesList.iterator();
		List<Triplet<User, Language, Users_Languages>> userLanguageList = new ArrayList<Triplet<User, Language, Users_Languages>>();
		List<Triplet<Comment, User, String>> commentsUser = new ArrayList<Triplet<Comment, User, String>>();

		while (it.hasNext()) {
			Users_Languages users_Languages = (Users_Languages) it.next();
			User user2 = userDAO.get(users_Languages.getIdu());
			Language language = languageDAO.get(users_Languages.getIdl());
			userLanguageList.add(new Triplet<User, Language, Users_Languages>(user2, language, users_Languages));
		}

		Iterator<Comment> it2 = comments.iterator();
		while (it2.hasNext()) {
			Comment comment = (Comment) it2.next();
			User user3 = userDAO.get(comment.getSender());
			String fecha = convertTime(comment.getTimeStamp());
			commentsUser.add(new Triplet<Comment, User, String>(comment, user3, fecha));

		}

		Integer idc = Integer.parseInt(request.getParameter("idc"));
		Comment comment = commentDAO.get(idc);

		request.setAttribute("commentEdit", comment);
		request.setAttribute("idu", userProfile.getIdu());
		request.setAttribute("permise", "editable");
		request.setAttribute("commentsUser", commentsUser);
		request.setAttribute("usersLanguages", userLanguageList);
		request.setAttribute("userProfile", userProfile);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/userProfile.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		UserDAO userDAO = new JDBCUserDAOImpl();

		commentDAO.setConnection(conn);
		userDAO.setConnection(conn);

		Integer idc = Integer.parseInt(request.getParameter("idc"));
		String iduS = request.getParameter("idu");
		User userProfile;
		if (iduS != null) {
			Integer idu = Integer.parseInt(iduS);
			userProfile = userDAO.get(idu);
		} else {
			iduS = (String) request.getAttribute("idu");
			Integer idu = Integer.parseInt(iduS);
			userProfile = userDAO.get(idu);
		}

		System.out.println("userProfile " + userProfile.getIdu());

		Comment c = commentDAO.get(idc);
		System.out.println(request.getParameter("comentario"));
		c.setText(request.getParameter("comentario"));
		List<String> messages = new ArrayList<>();
		if (c.validate(messages)) {
			commentDAO.save(c);
			request.setAttribute("commentEdit", c);
			request.setAttribute("permise", "noEditable");
			response.sendRedirect("ProfileServlet?idu=" + userProfile.getIdu());
		} else {
			request.setAttribute("messages", messages);
			request.setAttribute("commentEdit", c);
			request.setAttribute("permise", "noEditable");
			request.setAttribute("idu", userProfile.getIdu());
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/userProfile.jsp");
			view.forward(request, response);
		}

	}
}
