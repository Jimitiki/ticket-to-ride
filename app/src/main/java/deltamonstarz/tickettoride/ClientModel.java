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
	private int commandCounter;
	private boolean isStarted;
	private List<String> players;
	private List<GameInfo> availableGames;
	private BasePresenter presenter;
	private ClientGame game;

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
		return commandCounter;
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

	public boolean isStarted() {
		return isStarted;
	}

	public void startGame() {
		isStarted = true;
	}

	public void addPlayer(String username) {
		if (players.indexOf(username) == -1) {
			players.add(username);
		}
		notifyPresenter();
	}

	public void removePlayer(String username) {
		players.remove(username);
	}

	public void addLoginInformation(String username, String authToken) {
		this.username = username;
		this.authToken = authToken;
		notifyPresenter();
	}

	public void updateAvailableGames(List<GameInfo> games) {
		availableGames = games;
		notifyPresenter();
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

	public void incrementCommand() {
		commandCounter++;
	}

	public void clearUser() {
		username = null;
		authToken = null;
	}

	public void clearGame() {
		isStarted = false;
		gameID = -1;
		commandCounter = 0;
		players = new ArrayList<>();
	}

	private void notifyPresenter() {
		setChanged();
		synchronized (this) {
			notifyObservers();
		}
	}

	public ClientGame getGame() {return game;}
	public void setGame(ClientGame game) {this.game = game;}
}
