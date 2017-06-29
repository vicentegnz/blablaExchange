package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;

/**
 * Servlet implementation class DeleteCommentUserServlet
 */
@WebServlet(urlPatterns = { "/users/DeleteCommentUserServlet" })
public class DeleteCommentUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String convertTime(long time) {

		Date date = new Date(time);

		Format format = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

		return format.format(date);

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteCommentUserServlet() {
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
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		UserDAO userDAO = new JDBCUserDAOImpl();

		commentDAO.setConnection(conn);
		userDAO.setConnection(conn);

		String iduS = request.getParameter("idu");
		Integer idu = Integer.parseInt(iduS);
		User userProfile = userDAO.get(idu);

		Integer idc = Integer.parseInt(request.getParameter("idc"));
		commentDAO.delete(idc);
		response.sendRedirect("ProfileServlet?idu=" + userProfile.getIdu());

	}

}