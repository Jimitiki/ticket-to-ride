package deltamonstarz.tickettoride.model;

import java.util.List;
import java.util.Observable;

import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;
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
	public synchronized void setUsername(String username) {
		this.username = username;
	}
	public String getAuthToken() {
		return authToken;
	}
	public synchronized void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public int getGameID() {return game == null ? -1 : game.getGameID();}
	public synchronized void setGameID(int gameID) {game.setGameID(gameID);}
	public int getCurCommand() {return game.getCurCommand();}
	public List<GameInfo> getAvailableGames() {
		return availableGames;
	}
	public synchronized void drawTrainCard(TrainCard cardDrawn) {game.drawTrainCard(cardDrawn);}
	public synchronized void addDestCard(DestCard card) {game.addDestCard(card);}
	public synchronized void placeRoute(String player_username, Route route, boolean hasLongest) {game.placeRoute(player_username, route, hasLongest);}
	public synchronized void setBoard(Board board) {game.setBoard(board);}

	public synchronized void setAvailableGames(List<GameInfo> availableGames) {
		this.availableGames = availableGames;
	}

	public boolean isStarted() {return game.isStarted();}

	public synchronized void startGame() {
		game.setStarted(true);
		//DummyData.doTest();
	}

	public synchronized void addPlayer(String username) {
		game.addPlayer(username);
		notifyPresenter();
	}

	public synchronized void addLoginInformation(String username, String authToken) {
		this.username = username;
		this.authToken = authToken;
		notifyPresenter();
	}

	public synchronized void updateAvailableGames(List<GameInfo> games) {
		availableGames = games;
		notifyPresenter();
	}

	public synchronized void joinGame(int gameID) {
		newGame(gameID);
		notifyPresenter();
	}

	public BasePresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public synchronized void incrementCommand() { game.incrementCommand();}

	public synchronized void clearUser() {
		username = null;
		authToken = null;
	}

	public synchronized void clearGame() {
		game = null;
	}

	private synchronized void notifyPresenter() {
		setChanged();
//		synchronized (this) {
			notifyObservers();
//		}
	}

	public List<String> getPlayers() {
		return game.getPlayers();
	}

	public ClientGame getGame() {return game;}
	public synchronized void newGame(int gameID) {
		game = new ClientGame(gameID);
	}
}
