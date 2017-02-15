package deltamonstarz.tickettoride;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.commands.BaseCommand;
import deltamonstarz.tickettoride.presenters.BasePresenter;

public class ClientModel extends Observable{
	private static ClientModel clientModel = new ClientModel();
	private String username;
	private String authToken;
	private int gameID;
	private List<BaseCommand> gameHistory;
	private List<String> players;
	private List<GameInfo> availableGames;
	private BasePresenter presenter;

	private ClientModel() {
		gameID = -1;
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
		return gameHistory.size();
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

	public List<GameInfo> getAvailableGames() {
		return availableGames;
	}

	public void setAvailableGames(List<GameInfo> availableGames) {
		this.availableGames = availableGames;
	}

	public void addPlayer(String username) {
		players.add(username);
		setChanged();
		notifyObservers();
	}

	public void removePlayer(String username) {
		players.remove(username);
	}

	public void addLoginInformation(String username, String authToken) {
		this.username = username;
		this.authToken = authToken;
		notifyPresenter();
	}

	public void removeLoginInformation() {
		username = null;
		authToken = null;
		notifyPresenter();
	}

	public void updateAvailableGames(List<GameInfo> games) {
		availableGames = games;
		notifyPresenter();
	}

	/**
	 * Gets game commands given after curCommand and executes them
	 */
	public void updateGame(List<BaseCommand> commands) {
		for (BaseCommand command : commands) {
			command.execute();
		}
	}

	public void joinGame(int gameID) {
		this.gameID = gameID;
		players = new ArrayList<>();
		notifyPresenter();
	}

	public BasePresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	private void notifyPresenter() {
		setChanged();
		synchronized (this) {
			notifyObservers();
		}
	}
}
