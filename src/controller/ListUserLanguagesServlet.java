package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCLanguageDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
import model.Language;
import model.User;
import model.Users_Languages;
import util.Triplet;

/**
 * Servlet implementation class ListOrderServlet
 */
@WebServlet("/users/ListUserLanguagesServlet")
public class ListUserLanguagesServlet extends HttpServlet {

	public List<Triplet<User, Language, Users_Languages>> filter(String idioma, String nivel, String comunicacion,
			String orden) {

		List<Triplet<User, Language, Users_Languages>> userLanguageList = new ArrayList<Triplet<User, Language, Users_Languages>>();

		// COMO hago userDAO.getALL()

		return userLanguageList;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListUserLanguagesServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		// CONEXIONES BD
		UserDAO userDAO = new JDBCUserDAOImpl();
		Users_LanguagesDAO users_LanguagesDAO = new JDBCUsers_LanguagesDAOImpl();
		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();

		userDAO.setConnection(conn);
		languageDAO.setConnection(conn);
		users_LanguagesDAO.setConnection(conn);

		List<Language> languages = new ArrayList<>();
		List<User> users = new ArrayList<>();
		// ITERACCION DOGET
		List<Triplet<User, Language, Users_Languages>> userLanguageList = new ArrayList<Triplet<User, Language, Users_Languages>>();
		List<Users_Languages> users_LanguagesList = new ArrayList<>();
		languages = languageDAO.getAll();

		/* FILTRO DE USUARIOS */
		String stateFilter = request.getParameter("stateFilter");
		if (stateFilter != null && stateFilter.equals("active")) {
			String idioma = request.getParameter("idiomasFiltro");
			String nivel = request.getParameter("nivelIdiomasFiltro");
			String comunicacion = request.getParameter("comunicacionFiltro");

			users_LanguagesList = users_LanguagesDAO.getAll();
			Language la = languageDAO.get(idioma);
			users_LanguagesList = users_LanguagesDAO.getAllByLanguage(la.getIdl());
			int i = 0;
			while (i < users_LanguagesList.size()) {
				if (users_LanguagesList.get(i).getLevel().compareTo(nivel) < 0) {
					users_LanguagesList.remove(i);

				} else {
					if (userDAO.get(users_LanguagesList.get(i).getIdu()).getComunicacion().equals(comunicacion))
						users.add(userDAO.get(users_LanguagesList.get(i).getIdu()));
				}

				i++;
			}

			Iterator<Users_Languages> it = users_LanguagesList.iterator();
			while (it.hasNext()) {
				Users_Languages users_Languages = (Users_Languages) it.next();
				User user2 = userDAO.get(users_Languages.getIdu());
				Language language = languageDAO.get(users_Languages.getIdl());
				userLanguageList.add(new Triplet<User, Language, Users_Languages>(user2, language, users_Languages));
			}

		} else {

			String profileSearch = request.getParameter("search");
			if (profileSearch == null || profileSearch.equals("")) {
				users = userDAO.getAll();
				users_LanguagesList = users_LanguagesDAO.getAll();
				Iterator<Users_Languages> it = users_LanguagesList.iterator();
				while (it.hasNext()) {
					Users_Languages users_Languages = (Users_Languages) it.next();
					User user2 = userDAO.get(users_Languages.getIdu());
					Language language = languageDAO.get(users_Languages.getIdl());

					userLanguageList
							.add(new Triplet<User, Language, Users_Languages>(user2, language, users_Languages));
				}

			} else {
				if (userDAO.get(profileSearch) != null) {
					User user = userDAO.get(profileSearch);
					if (user != null) {
						users.add(user);
						users_LanguagesList = users_LanguagesDAO.getAllByUser(users.get(0).getIdu());
						Iterator<Users_Languages> it = users_LanguagesList.iterator();
						while (it.hasNext()) {
							Users_Languages users_Languages = (Users_Languages) it.next();
							User user2 = userDAO.get(users_Languages.getIdu());
							Language language = languageDAO.get(users_Languages.getIdl());
							userLanguageList.add(
									new Triplet<User, Language, Users_Languages>(user2, language, users_Languages));
						}
					}
				}

			}
		}
		request.setAttribute("message", "No se ha encontrado ningún usuario con tu busqueda.");
		request.setAttribute("languages", languages);
		request.setAttribute("users", users);
		request.setAttribute("usersLanguages", userLanguageList);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/home.jsp");
		view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
