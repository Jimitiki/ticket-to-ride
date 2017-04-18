package delta.monstarz.shared.commands;

import java.io.Serializable;

public class BaseCommand implements Serializable, Comparable<BaseCommand> {
    protected String name;
    protected String username;
    protected int gameID;
	protected boolean isGlobal;
	protected boolean expires = false;
	protected int id;

	public BaseCommand(String username) {
		this.username = username;
	}

	public BaseCommand(String username, int gameID) {
		this.username = username;
		this.gameID = gameID;
	}

	public void execute() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(boolean global) {
		isGlobal = global;
	}

	public boolean expires() {
		return expires;
	}

	public void setExpires(boolean expires) {
		this.expires = expires;
	}

	@Override
	public int compareTo(BaseCommand o) {
		return this.id - o.getId();
	}

	@Override
	public String toString() {
		return name + " : " + String.valueOf(id);
	}
}
