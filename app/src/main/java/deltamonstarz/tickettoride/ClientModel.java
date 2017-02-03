package deltamonstarz.tickettoride;

import java.util.List;

import delta.monstarz.commands.BaseCommand;

public class ClientModel {
	private static ClientModel clientModel = new ClientModel();
	private String username;
	private String authToken;
	private String gameID;
	private int curCommand;
	private List<BaseCommand> gameHistory;

	private ClientModel() {}

	public static ClientModel getInstance() {
		return clientModel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public int getCurCommand() {
		return curCommand;
	}

	public void setCurCommand(int curCommand) {
		this.curCommand = curCommand;
	}

	public List<BaseCommand> getGameHistory() {
		return gameHistory;
	}

	public void setGameHistory(List<BaseCommand> gameHistory) {
		this.gameHistory = gameHistory;
	}

	/**
	 * Gets game commands given after curCommand and executes them
	 */
	public void updateGame() {

	}
}
