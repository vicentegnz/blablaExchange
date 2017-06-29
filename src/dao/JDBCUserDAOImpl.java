package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.User;

public class JDBCUserDAOImpl implements UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());

	@Override
	public User get(long idu) {
		if (conn == null)
			return null;

		User user = null;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.User WHERE idu =" + idu);
			if (!rs.next())
				return null;
			user = new User();
			user.setIdu(rs.getLong("idu"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setLocalizacion(rs.getString("localizacion"));
			user.setComunicacion(rs.getString("comunicacion"));
			logger.info("fetching User by idu: " + idu + " -> " + user.getIdu() + " " + user.getUsername() + " "
					+ user.getEmail() + " " + user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User get(String username) {
		if (conn == null)
			return null;

		User user = null;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.User WHERE username ='" + username + "'");
			if (!rs.next())
				return null;
			user = new User();
			user.setIdu(rs.getLong("idu"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setLocalizacion(rs.getString("localizacion"));
			user.setComunicacion(rs.getString("comunicacion"));
			logger.info("fetching User by name: " + username + " -> " + user.getIdu() + " " + user.getUsername() + " "
					+ user.getEmail() + " " + user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public List<User> getAll() {

		if (conn == null)
			return null;

		ArrayList<User> users = new ArrayList<User>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized (conn) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM Public.User");
			}
			while (rs.next()) {
				User user = new User();
				user.setIdu(rs.getLong("idu"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setLocalizacion(rs.getString("localizacion"));
				user.setComunicacion(rs.getString("comunicacion"));
				// user.setPassword(rs.getString("password"));
				user.setPassword("********");// al devolver todos los usuarios
												// no devolvemos la contraseña
												// visible

				users.add(user);
				logger.info("fetching users: " + user.getIdu() + " " + user.getUsername() + " " + user.getEmail() + " "
						+ user.getPassword());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public long add(User user) {
		long idu = -1;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(
						"INSERT INTO User (username,email,password,localizacion,comunicacion) VALUES('"
								+ user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword() + "','"
								+ user.getLocalizacion() + "','" + user.getComunicacion() + "')",
						Statement.RETURN_GENERATED_KEYS);

				ResultSet genKeys = stmt.getGeneratedKeys();

				if (genKeys.next())
					idu = genKeys.getInt(1);

				logger.info("creating User(" + idu + "): " + user.getIdu() + " " + user.getUsername() + " "
						+ user.getEmail() + " " + user.getPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return idu;
	}

	@Override
	public boolean save(User user) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE User SET username='" + user.getUsername() + "', email='" + user.getEmail()
						+ "', password='" + user.getPassword() + "', localizacion='" + user.getLocalizacion()
						+ "', comunicacion='" + user.getComunicacion() + "' WHERE idu = " + user.getIdu());
				logger.info("updating User: " + user.getIdu() + " " + user.getUsername() + " " + user.getEmail() + " "
						+ user.getPassword());
				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return done;

	}

	@Override
	public boolean delete(long idu) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM User WHERE idu =" + idu);
				logger.info("deleting User: " + idu);
				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

}
