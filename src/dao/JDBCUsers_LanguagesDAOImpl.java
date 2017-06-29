package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Users_Languages;



public class JDBCUsers_LanguagesDAOImpl implements Users_LanguagesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUsers_LanguagesDAOImpl.class.getName());

	@Override
	public List<Users_Languages> getAll() {

		if (conn == null) return null;
						
		ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages");
						
			while ( rs.next() ) {
				Users_Languages users_Languages = new Users_Languages();
				users_Languages.setIdu(rs.getLong("idu"));
				users_Languages.setIdl(rs.getLong("idl"));
				users_Languages.setLevel(rs.getString("level"));
							
				users_LanguagesList.add(users_Languages);
				logger.info("fetching users_LanguagesList: "+users_Languages.getIdu()+" "+users_Languages.getIdl()+" "+users_Languages.getLevel());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users_LanguagesList;
	}

	@Override
	public List<Users_Languages> getAllByUser(long idu) {
		
		if (conn == null) return null;
						
		ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages WHERE idu="+idu);

			while ( rs.next() ) {
				Users_Languages users_Languages = new Users_Languages();
				users_Languages.setIdu(rs.getLong("idu"));
				users_Languages.setIdl(rs.getLong("idl"));
				users_Languages.setLevel(rs.getString("level"));
							
				users_LanguagesList.add(users_Languages);
				logger.info("fetching users_LanguagesList: "+users_Languages.getIdu()+" "+users_Languages.getIdl()+" "+users_Languages.getLevel());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users_LanguagesList;
	}
	
	@Override
	public List<Users_Languages> getAllByLanguage(long idl) {
		
		if (conn == null) return null;
						
		ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages WHERE idl="+idl);

			while ( rs.next() ) {
				Users_Languages users_Languages = new Users_Languages();
				users_Languages.setIdu(rs.getLong("idu"));
				users_Languages.setIdl(rs.getLong("idl"));
				users_Languages.setLevel(rs.getString("level"));
							
				users_LanguagesList.add(users_Languages);
				logger.info("fetching users_LanguagesList: "+users_Languages.getIdu()+" "+users_Languages.getIdl()+" "+users_Languages.getLevel());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users_LanguagesList;
	}
	
	
	
	
	
	@Override
	public Users_Languages get(long idu,long idl) {
		if (conn == null) return null;
		
		Users_Languages users_Languages = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages WHERE idu="+idu+" AND idl="+idl);			 
			if (!rs.next()) return null;
			users_Languages= new Users_Languages();
			users_Languages.setIdu(rs.getLong("idu"));
			users_Languages.setIdl(rs.getLong("idl"));
			users_Languages.setLevel(rs.getString("level"));
			
			logger.info("fetching users_Languages by idu: "+users_Languages.getIdu()+"  and idl: "+users_Languages.getIdl()+" "+users_Languages.getLevel());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users_Languages;
	}
	
	

	@Override
	public boolean add(Users_Languages users_Languages) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Users_Languages (idu,idl,level) VALUES('"+
									users_Languages.getIdu()+"','"+
									users_Languages.getIdl()+"','"+
									users_Languages.getLevel()+"')");
						
				logger.info("creating Users_Languages:("+users_Languages.getIdu()+" "+users_Languages.getIdl()+" "+users_Languages.getLevel());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(Users_Languages users_Languages) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Users_Languages SET level='"+
									users_Languages.getLevel()+"' WHERE idu = "+users_Languages.getIdu()+" AND idl="+users_Languages.getIdl());
				
				logger.info("updating Users_Languages: "+users_Languages.getIdu()+"  and idl: "+users_Languages.getIdl()+" "+users_Languages.getLevel());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idu, long idl) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Users_Languages WHERE idu ="+idu+" AND idl="+idl);
				logger.info("deleting Users_Languages: "+idu+" , idl="+idl);
				done= true;
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
