package delta.monstarz.shared.commands;

public abstract class BaseCommand {
    protected String name;
    protected String username;
    protected String gameID;

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

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
}
