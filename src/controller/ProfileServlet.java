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
 * Servlet implementation class ProfileServlet
 */
@WebServlet(urlPatterns = { "/users/ProfileServlet" })
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String permise = "noEditable";

	public String convertTime(long time) {

		Date date = new Date(time);

		Format format = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

		return format.format(date);

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileServlet() {
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
		userDAO.setConnection(conn);
		users_LanguagesDAO.setConnection(conn);
		languageDAO.setConnection(conn);
		commentDAO.setConnection(conn);

		String iduS = request.getParameter("idu");
		User userProfile;
		List<Comment> comments;
		if (iduS != null) {
			Integer idu = Integer.parseInt(iduS);
			userProfile = userDAO.get(idu);
			comments = commentDAO.getAllByReceiver(idu);
		} else {

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
		request.setAttribute("userReceiver", request.getAttribute("userProfile"));
		request.setAttribute("commentToEdit", request.getAttribute("commentEdit"));

		if (request.getAttribute("permise") == null) {
			request.setAttribute("permise", permise);
		} else {
			request.setAttribute("permise", request.getAttribute("permise"));
		}

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
		// TODO Auto-generated method stub
	}

}
