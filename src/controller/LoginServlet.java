package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;
import util.EncryptedAlgorithm;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null)
			response.sendRedirect("users/ListUserLanguagesServlet");
		else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		logger.info("credentials: " + username + " - " + EncryptedAlgorithm.getHash(password));

		User user = userDao.get(username);

		if ((user != null) && (user.getPassword().equals(EncryptedAlgorithm.getHash(password)))) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect("pages/index.html");

		} else {
			request.setAttribute("messages", "Wrong username or password!!");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");
			view.forward(request, response);
		}
	}

}
