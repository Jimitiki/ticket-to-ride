package deltamonstarz.tickettoride;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import delta.monstarz.shared.commands.BaseCommand;

public class ClientModel extends Observable{
	private static ClientModel clientModel = new ClientModel();
	private String username;
	private String authToken;
	private int gameID;
	private int curCommand;
	private List<BaseCommand> gameHistory;
	private List<String> players;

	private ClientModel() {
		gameID = -1;
		curCommand = -1;
	}

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

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
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

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public void addPlayer(String username) {
		players.add(username);
	}

	public void removePlayer(String username) {
		players.remove(username);
	}

	/**
	 * Gets game commands given after curCommand and executes them
	 */
	public void updateGame(List<BaseCommand> commands) {
		for (BaseCommand command : commands) {
			command.execute();
		}
	}
}
