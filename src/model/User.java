package model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private long idu;
	private String username;
	private String password;
	private String email;

	private String localizacion;
	private String comunicacion;

	// private String localizacion;
	// private String biografia;

	public boolean validate(List<String> validationMessages) {
		if (username == null || username.trim().isEmpty()) {
			validationMessages.add("Rellena el nombre de usuario.");
		} else if (username.length() > 16) {
			validationMessages.add("El nombre de usuario no puede sobrepasar 16 caracteres.");
		} else if (username.length() < 3) {
			validationMessages.add("El nombre de usuario debe ser de al menos 3 caracteres.");
		} else if (username.contains(" ")) {
			validationMessages.add("El nombre de usuario no puede contener espacios.");
		} else if (!username.matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
			validationMessages.add("Nombre de usuario no válido.");
		}

		if (password == null || password.trim().isEmpty()) {
			validationMessages.add("Rellena la contraseña.");
		} else if (password.length() < 6) {
			validationMessages.add("La contraseña debe tener al menos 6 caracteres.");
		} else if (password.contains(" ")) {
			validationMessages.add("La contraseña no puede contener espacios.");
		} else if (!password.matches("(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*")) {
			validationMessages.add("Contraseña no valida. Debe contener al menos una mayúscula y un número.");
		}

		if (email == null || email.trim().isEmpty()) {
			validationMessages.add("Rellena el email.");
		} else if (email.length() > 50) {
			validationMessages.add("El email no puede sobrepasar los 50 carácteres.");
		} else if (email.contains(" ")) {
			validationMessages.add("El email no puede contener espacios.");
		} else if (!email.matches("[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+")) {
			validationMessages.add("Email no válido.");
		}

		// if (biografia == null || biografia.trim().isEmpty()) {
		// validationMessages.add("Rellena la Biografia.");
		// } else if (email.length() < 20) {
		// validationMessages.add("La biografia debe tener al menos 20
		// carácteres.");
		// }
		if (validationMessages.isEmpty())
			return true;
		else {
			System.out.println(validationMessages.get(0).toString());
			return false;
		}
	}

	public long getIdu() {
		return idu;
	}

	public void setIdu(long idu) {
		this.idu = idu;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComunicacion() {
		return comunicacion;
	}

	public void setComunicacion(String comunicacion) {
		this.comunicacion = comunicacion;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

}
