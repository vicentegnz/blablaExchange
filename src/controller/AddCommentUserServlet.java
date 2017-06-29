package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.Comment;
import model.User;

/**
 * Servlet implementation class AddCommentUserServlet
 */

@WebServlet(urlPatterns = { "/users/AddCommentUserServlet" })
public class AddCommentUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCommentUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		UserDAO userDAO = new JDBCUserDAOImpl();

		commentDAO.setConnection(conn);
		userDAO.setConnection(conn);

		String iduS = request.getParameter("idu");

		Integer idu = Integer.parseInt(iduS);
		User userProfile = userDAO.get(idu);
		String texto = request.getParameter("comentario");
		Comment c = new Comment();
		List<String> messages = new ArrayList<>();

		c.setText(texto);
		c.setReceiver(userProfile.getIdu());
		c.setSender(user.getIdu());

		if (c.validate(messages)) {
			commentDAO.add(c);
			response.sendRedirect("ProfileServlet?idu=" + userProfile.getIdu());

		} else {
			request.setAttribute("messages", messages);
			request.setAttribute("messages", messages);
			request.setAttribute("commentEdit", c);
			request.setAttribute("permise", "noEditable");
			request.setAttribute("idu", userProfile.getIdu());
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/userProfile.jsp");
			view.forward(request, response);
		}
	}

}
