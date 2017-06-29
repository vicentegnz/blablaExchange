package model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Users_Languages {
	private long idu;
	private long idl;
	private String level;

	public boolean validate(List<String> validationMessages) {
		if (level == null || level.trim().isEmpty()) {
			validationMessages.add("Rellena el nivel del usuario.");
		} else if (level.length() > 7) {
			validationMessages.add("El nivel no puede sobrepasar 6 caracteres.");
		} else if (level.length() < 2) {
			validationMessages.add("El nivel debe ser de al menos 2 caracteres.");
		} else if (level.contains(" ")) {
			validationMessages.add("El nivel no puede contener espacios.");
		} else if (!level.matches("[a-zA-Z][a-zA-Z0-9]*")) {
			validationMessages.add("Nivel de lenguaje no válido.");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public long getIdu() {
		return idu;
	}

	public void setIdu(long idu) {
		this.idu = idu;
	}

	public long getIdl() {
		return idl;
	}

	public void setIdl(long idl) {
		this.idl = idl;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String name) {
		this.level = name;
	}

}
