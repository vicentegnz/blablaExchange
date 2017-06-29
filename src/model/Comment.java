package model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {

	private long idc;
	private long sender;
	private long receiver;
	private long timeStamp;
	private String text;

	public boolean validate(List<String> validationMessages) {

		if (text == null || text.trim().isEmpty() || text.length() < 11) {
			validationMessages.add("El comentario debe incluir más de 10 caracteres.");
		} else if (text.length() > 400) {
			validationMessages.add("El contenido no puede sobrepasar los 400 caracteres.");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getIdc() {
		return idc;
	}

	public void setIdc(long idc) {
		this.idc = idc;
	}

	public long getSender() {
		return sender;
	}

	public void setSender(long sender) {
		this.sender = sender;
	}

	public long getReceiver() {
		return receiver;
	}

	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
