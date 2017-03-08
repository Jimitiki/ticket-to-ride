package deltamonstarz.tickettoride.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.Message;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.PlayerInfo;
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

	public int getGameID() {
		return game == null ? -1 : game.getGameID();
	}

	public synchronized void setGameID(int gameID) {
		game.setGameID(gameID);
	}

	public int getCurCommand() {
		return game.getCurCommand();
	}

	public List<GameInfo> getAvailableGames() {
		return availableGames;
	}

	public synchronized void drawTrainCard(TrainCard cardDrawn) {
		game.drawTrainCard(cardDrawn);
		notifyPresenter(UpdateType.TRAIN_CARD);
	}

	public synchronized void addDestCard(DestCard card) {
		game.addDestCard(card);
	}

	public synchronized void placeRoute(String player_username, Route route, boolean hasLongest) {
		game.placeRoute(player_username, route, hasLongest);
	}

	public synchronized void setBoard(Board board) {
		game.setBoard(board);
	}

	public synchronized void setAvailableGames(List<GameInfo> availableGames) {
		this.availableGames = availableGames;
	}

	public boolean isStarted() {return game.isStarted();}

	public synchronized void startGame(Board board) {
		game.setStarted(true);
		setBoard(board);
		notifyPresenter(UpdateType.START_GAME);
		//DummyData.doTest();
	}

	public synchronized void addPlayer(String username) {
		game.addPlayer(username);
		notifyPresenter(UpdateType.USER_JOIN);
	}

	public synchronized void addLoginInformation(String username, String authToken) {
		this.username = username;
		this.authToken = authToken;
		notifyPresenter(UpdateType.LOGIN);
	}

	public synchronized void updateAvailableGames(List<GameInfo> games) {
		availableGames = games;
		notifyPresenter(UpdateType.GAME_LIST);
	}

	public synchronized void joinGame(int gameID) {
		newGame(gameID);
		notifyPresenter(UpdateType.JOIN_GAME);
	}

	public BasePresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public synchronized void setLastCommandID(int lastID) { game.setLastCommandID(lastID);}

	public synchronized void clearUser() {
		username = null;
		authToken = null;
		notifyPresenter(UpdateType.LOGOUT);
	}

	public synchronized void clearGame() {
		game = null;
		notifyPresenter(UpdateType.LEAVE_GAME);
	}

	private synchronized void notifyPresenter(UpdateType updateType) {
		setChanged();
//		synchronized (this) {
			notifyObservers(updateType);
//		}
	}

	public List<String> getPlayers() {
		return game.getPlayers();
	}

	public ClientGame getGame() {return game;}
	public synchronized void newGame(int gameID) {
		game = new ClientGame(gameID);
	}

	public void drawDestinationCards(ArrayList<DestCard> choices, int minSelection) {
		setDestCardChoices(choices);
		setMinSelection(minSelection);
		notifyPresenter(UpdateType.DRAW_DEST_CARDS);
	}

	public void setDestCardChoices(ArrayList<DestCard> choices) {
		game.setDestCardChoices(choices);
	}
	public ArrayList<DestCard> getDestCardChoices() {return game.getDestCardChoices();}

	public List<Message> getChatHistory() {
		return game.getChatHistory();
	}

	public int getMinSelection() {
		return game.getMinSelection();
	}

	public void setMinSelection(int minSelection) {
		game.setMinSelection(minSelection);
	}

	public Message getLastMessage() {
		return game.getLastMessage();
	}

	public void addMessage(Message message) {
		game.addMessage(message);
		notifyPresenter(UpdateType.CHAT);
	}

	public void updatePlayerInfo(PlayerInfo player_info) {
		game.updatePlayerInfo(player_info);
		if (game.isStarted()) {
			notifyPresenter(UpdateType.PLAYER_INFO);
		}
	}

	public String getMapImagePath() {
		return game.getMapImagePath();
	}

	public List<DestCard> getDestinationCards() {
		return game.getMe().getDestCards();
	}

}
