package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
import model.User;
import model.Users_Languages;

/**
 * Servlet implementation class AddLanguage
 */
@WebServlet(urlPatterns = { "/users/AddLanguageServlet" })
public class AddLanguageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddLanguageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Connection conn = (Connection)
		// getServletContext().getAttribute("dbConn");
		//
		// HttpSession session = request.getSession();
		// User user = (User) session.getAttribute("user");
		//
		// LanguageDAO languageDao = new JDBCLanguageDAOImpl();
		// Users_LanguagesDAO userLanguageDao = new
		// JDBCUsers_LanguagesDAOImpl();
		// UserDAO userDao = new JDBCUserDAOImpl();
		//
		// languageDao.setConnection(conn);
		// userDao.setConnection(conn);
		// userLanguageDao.setConnection(conn);
		//
		// List<Language> languages = new ArrayList<>();
		// List<Users_Languages> userLanguages = new ArrayList<>();
		//
		// languages = languageDao.getAll();
		//
		// if (user != null) {
		// userLanguages = userLanguageDao.getAllByUser(user.getIdu());
		// List<Pair<Language, String>> listLanguage = new
		// ArrayList<Pair<Language, String>>();
		// int i = 0;
		// while (i < userLanguages.size()) {
		// listLanguage.add(new Pair<Language,
		// String>(languageDao.get(userLanguages.get(i).getIdl()),
		// userLanguages.get(i).getLevel()));
		// i++;
		// }
		// request.setAttribute("userLanguages", listLanguage);
		//
		// }
		//
		// request.setAttribute("languages", languages);
		//
		// RequestDispatcher view =
		// request.getRequestDispatcher("/WEB-INF/editProfile.jsp");
		// view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		UserDAO userDAO = new JDBCUserDAOImpl();
		Users_LanguagesDAO userLanguageDao = new JDBCUsers_LanguagesDAOImpl();
		userDAO.setConnection(conn);
		userLanguageDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Users_Languages userL = new Users_Languages();
		
		userL.setIdu(user.getIdu());
		userL.setLevel(request.getParameter("nivelIdioma"));
		userL.setIdl(Integer.parseInt(request.getParameter("idiomaHablado")));

		List<String> messages = new ArrayList<>();

		if (userL.validate(messages)) {
			userLanguageDao.add(userL);
			response.sendRedirect("EditProfileServlet");


		} else {
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("/WEB-INF/editProfile.jsp").forward(request, response);
		}
	}

}
