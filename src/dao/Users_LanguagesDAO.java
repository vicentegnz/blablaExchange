package dao;

import java.sql.Connection;
import java.util.List;

import model.Users_Languages;



public interface Users_LanguagesDAO {

	/**
	 * Asocia la conexión a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 */
	public void setConnection(Connection conn);

	/**
	 * Obtiene todos los lenguajes de todos los usuarios de la base de datos.
	 * 
	 * @return Lista con todos los lenguajes de los usuarios de la base de datos.
	 */
	public List<Users_Languages> getAll();

	/**
	 * Obtiene todos los usuarios-lenguajes-nivel en función de un usuario dado.
	 * 
	 * @param idu
	 *            Identificador del usuario del que se quieren recuperar todos los usuarios-lenguajes-nivel
	 * 
	 * @return Lista con todos los usuarios-lenguajes-nivel en función de un usuario dado.
	 */
	public List<Users_Languages> getAllByUser(long idu);
	
	/**
	 * Obtiene todos los usuarios-lenguajes-nivel en función de un lenguaje dado.
	 * 
	 * @param idl
	 *            Identificador del lenguaje del que se quieren recuperar todos los usuarios-lenguajes-nivel
	 * 
	 * @return Lista con todos los usuarios-lenguajes-nivel en función de un lenguaje dado.
	 */
	public List<Users_Languages> getAllByLanguage(long idl);

	/**
	 * Obtiene el usuario-lenguaje-nivel en función de un usuario y un lenguaje dado.
	
	 * @param idu
	 *            Identificador del usuario del que se quieren recuperar usuario-lenguaje-nivel
	 * 	 * 
	 * @param idl
	 *            Identificador del lenguaje del que se quieren recuperar usuario-lenguaje-nivel
	 * 
	 * @return  usuario-lenguaje-nivel en función de un usuario y un lenguaje dado.
	 * */
	public Users_Languages get(long idu,long idl);

	/**
	 * Añade un usuario-lenguaje-nivel a la base de datos.
	 * 
	 * @param user_language
	 *            Objeto que contiene la información relativa al usuario-lenguaje-nivel que
	 *            se pretende añadir.
	 * 
	 * @return True si la operación ha tenido éxito. False en caso contrario.
	 */
	public boolean add(Users_Languages user_language);

	/**
	 * Actualiza un usuario-lenguaje-nivel ya existente.
	 * 
	 * @param user_language
	 *            usuario-lenguaje-nivel que se pretende actualizar.
	 * 
	 * @return True si la operación ha tenido éxito. False en caso contrario.
	 */
	public boolean save(Users_Languages user_language);

	/**
	 * Elimina un usuario-lenguaje-nivel de la base de datos.
	 * 
	 *  @param idu
	 *            Identificador del usuario del que se quiere eliminar usuario-lenguaje-nivel
	 * 	 * 
	 * @param idl
	 *            Identificador del lenguaje del que se quiere eliminar usuario-lenguaje-nivel
	 * 
	 * 
	 * @return True si la operación ha tenido éxito. False en caso contrario.
	 */
	public boolean delete(long idu, long idl);
}