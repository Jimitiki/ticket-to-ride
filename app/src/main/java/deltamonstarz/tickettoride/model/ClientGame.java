package deltamonstarz.tickettoride.model;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.Message;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.PlayerInfo;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.model.player.ClientPlayer;

public class ClientGame {
    private Board board;
    private ClientPlayer me;
    private List<PlayerInfo> playerInfos = new ArrayList<>();
    private boolean started;
    private int lastCommandID;
    private int gameID;
	private List<String> players;
	private List<Message> chatHistory;
	private List<TrainCard> faceUpCards;
	private CardColor mostRecentCardColor;

	public ClientGame(int id) {
		gameID = id;
		players = new ArrayList<>();
		me = new ClientPlayer(ClientModel.getInstance().getUsername());
		lastCommandID = -1;
		faceUpCards = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			faceUpCards.add(null);
		}
	}

	public TrainCard drawFaceupTrainCard(int cardSpot, TrainCard replacementCard) {
		TrainCard cardDrawn = faceUpCards.get(cardSpot);
		faceUpCards.set(cardSpot, replacementCard);
		ClientModel.getInstance().notifyPresenter(UpdateType.FACE_UP_CARD);
		return cardDrawn;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getCurCommand() {
		return lastCommandID;
	}

	public boolean isStarted() {
		return started;
	}

    public void setStarted(boolean started) {
		this.started = started;
//		opps = new ArrayList<>();
	    chatHistory = new ArrayList<>();
//		for (String username : players) {
//			if (ClientModel.getInstance().getUsername().equals(username)) {
//				opps.add(new PlayerInfo(username));
//			}
//		}
	}


	public List<PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}

	// Iterate through current playerInfos and replace the one with a matching username
	// else just add the player_info to the end.
	public void updatePlayerInfo(PlayerInfo player_info) {
		for (int i = 0; i < playerInfos.size(); i++) {
			if (playerInfos.get(i).getUsername().equals(player_info.getUsername()) ) {
				playerInfos.set(i, player_info);
				return;
			}
		}
		//if no matches
		playerInfos.add(player_info);
	}

	public ClientPlayer getMe() {
		return me;
	}

	public void setMe(ClientPlayer me) {
		this.me = me;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setLastCommandID(int lastID) {
		lastCommandID = lastID;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void drawTrainCard(TrainCard cardDrawn) {
		me.drawTrainCard(cardDrawn);
		mostRecentCardColor = cardDrawn.getColor();
		ClientModel.getInstance().notifyPresenter(UpdateType.REPORT_DRAWN_CARD);
	}

	public void addDestCards(ArrayList<DestCard> cards) {
		me.selectDestinationCards(cards);
	}

	public void placeRoute(String username, int routeID) {
		Route route = board.getRouteByID(routeID);
		for (PlayerInfo player : playerInfos) {
			if (player.getUsername().equals(username)) {
				route.claim(username, player.getPlayerColor());
				return;
			}
		}
	}

	public void addPlayer(String username) {
		if (players.indexOf(username) == -1) {
			players.add(username);
		}
	}

	public void setDestCardChoices(ArrayList<DestCard> choices) {
		me.setDestCardChoices(choices);
	}

	public ArrayList<DestCard> getDestCardChoices() {
		return me.getDestCardChoices();
	}

	public int getMinSelection() {
		return me.getMinSelection();
	}

	public void setMinSelection(int minSelection) {
		me.setMinSelection(minSelection);
	}

	public List<Message> getChatHistory() {
		return chatHistory;
	}

	public Message getLastMessage() {
		return chatHistory.get(chatHistory.size() - 1);
	}

	public void addMessage(Message message) {
		chatHistory.add(message);
	}

	public String getMapImagePath() {
		return board.getImageID();
	}

	public List<TrainCard> getFaceUpCards() {
		return faceUpCards;
	}

	public CardColor getMostRecentCardColor() {
		return mostRecentCardColor;
	}
}
