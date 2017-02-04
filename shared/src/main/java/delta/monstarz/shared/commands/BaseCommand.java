package delta.monstarz.shared.commands;

public abstract class BaseCommand {
    protected String name;
    protected String username;
    protected int gameID;
	protected boolean isGlobal;

    public abstract void execute();

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
}
