package deltamonstarz.tickettoride;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.commands.BaseCommand;
import deltamonstarz.tickettoride.model.ClientGame;
import deltamonstarz.tickettoride.model.DummyData;
import deltamonstarz.tickettoride.presenters.BasePresenter;

public class ClientModel extends Observable{
	private static ClientModel clientModel = new ClientModel();
	private String username;
	private String authToken;
	private List<GameInfo> availableGames;
	private BasePresenter presenter;
	private ClientGame game;

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
	public int getGameID() {return game == null ? -1 : game.getGameID();}
	public void setGameID(int gameID) {game.setGameID(gameID);}
	public int getCurCommand() {return game.getCurCommand();}
	public List<GameInfo> getAvailableGames() {
		return availableGames;
	}

	public void setAvailableGames(List<GameInfo> availableGames) {
		this.availableGames = availableGames;
	}

	public boolean isStarted() {return game.isStarted();}

	public void startGame() {
		game.setStarted(true);
		DummyData.doTest();
	}

	public void addPlayer(String username) {
		game.addPlayer(username);
		notifyPresenter();
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
		newGame(gameID);
		notifyPresenter();
	}

	public BasePresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public void incrementCommand() { game.incrementCommand();}

	public void clearUser() {
		username = null;
		authToken = null;
	}

	public void clearGame() {
		game = null;
	}

	private void notifyPresenter() {
		setChanged();
		synchronized (this) {
			notifyObservers();
		}
	}

	public List<String> getPlayers() {
		return game.getPlayers();
	}

	public ClientGame getGame() {return game;}
	public void newGame(int gameID) {
		game = new ClientGame(gameID);
	}
}
