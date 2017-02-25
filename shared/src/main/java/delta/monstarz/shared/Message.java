package delta.monstarz.shared;

import java.awt.Color;

public class Message {
	private String message;
	private String username;

	public Message(String message, String username) {
		this.message = message;
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public String getUsername() {
		return username;
	}
}
