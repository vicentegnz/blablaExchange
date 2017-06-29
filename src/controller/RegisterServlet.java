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

import dao.JDBCLanguageDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.LanguageDAO;
import dao.UserDAO;
import model.Language;
import model.User;
import util.EncryptedAlgorithm;

/**
 * Servlet implementation class EditRegister
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		LanguageDAO languageDao = new JDBCLanguageDAOImpl();

		languageDao.setConnection(conn);

		List<Language> languages = new ArrayList<>();
		languages = languageDao.getAll();
		request.setAttribute("languages", languages);

		response.sendRedirect("pageRegister/register.html");
		/**
		 * PARA USAR CON SERVLET RequestDispatcher view =
		 * request.getRequestDispatcher("/WEB-INF/register.jsp");
		 * view.forward(request, response);
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<>();

		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setEmail(request.getParameter("email"));
		String password = request.getParameter("password");
		String rpassword = request.getParameter("rpassword");
		user.setComunicacion(request.getParameter("tipoIntercambio"));
		user.setLocalizacion(request.getParameter("localizacion"));
		if (password != null || rpassword != null) {
			if (!password.equals(rpassword))
				messages.add("Las contraseñas no son iguales");
			else
				user.setPassword(password);
		}
		System.out.println("User: " + user.getUsername());
		System.out.println("Password: " + password);

		if (user.validate(messages)) {
			if (password != null || rpassword != null)
				user.setPassword(EncryptedAlgorithm.getHash(password));

			session.setAttribute("user", user);
			userDAO.add(user);
			response.sendRedirect("RegisterServlet");

		} else {
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
		}
	}

}
