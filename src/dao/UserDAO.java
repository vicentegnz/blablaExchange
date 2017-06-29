package dao;

import java.sql.Connection;
import java.util.List;

import model.User;


public interface UserDAO {

	/**
	 * Asocia la conexión a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 */
	public void setConnection(Connection conn);

	/**
	 * Obtiene un usuario de la base de datos.
	 * 
	 * @param idu
	 *            Identificador del usuario.
	 * 
	 * @return Usuario con el identificador pasado.
	 */
	public User get(long idu);

	/**
	 * Obtiene un usuario dado su nombre.
	 * 
	 * @param username
	 *            Nombre del usuario que se pretende recuperar.
	 * 
	 * @return Usuario recuperado.
	 */
	public User get(String username);

	/**
	 * Obtiene todos los usuarios de la base de datos.
	 * 
	 * @return Lista con todos los usuarios de la base de datos.
	 */
	public List<User> getAll();

	/**
	 * Añade un usuario a la base de datos.
	 * 
	 * @param user
	 *            Objeto que contiene la información relativa al usuario que se
	 *            pretende añadir.
	 * 
	 * @return Identificador de usuario introducido o -1 si ha fallado la
	 *         operación.
	 */
	public long add(User user);

	/**
	 * Actualiza un usuario ya existente.
	 * 
	 * @param user
	 *            Usuario que se pretende actualizar.
	 * 
	 * @return True si la operación ha tenido éxito. False en caso contrario.
	 */
	public boolean save(User user);

	/**
	 * Elimina un usuario de la base de datos.
	 * 
	 * @param idu
	 *            Identificador del usuario que se pretende eliminar.
	 * 
	 * @return True si la operación ha tenido éxito. False en caso contrario.
	 */
	public boolean delete(long idu);
}
