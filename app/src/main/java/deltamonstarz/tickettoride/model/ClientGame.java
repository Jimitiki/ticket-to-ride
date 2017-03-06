package deltamonstarz.tickettoride.model;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.Message;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.PlayerInfo;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;

public class ClientGame {
    private Board board;
    private Player me;
    private List<PlayerInfo> playerInfos;
    private boolean started;
    private int lastCommandID;
    private int gameID;
	private List<String> players;
	private List<Message> chatHistory;

	public ClientGame(int id) {
		gameID = id;
		players = new ArrayList<>();
		me = new Player(ClientModel.getInstance().getUsername());
		lastCommandID = -1;
	}

    public int getGameID() { return gameID; }
    public void setGameID(int gameID) { this.gameID = gameID; }
    public int getCurCommand() { return lastCommandID; }
    public boolean isStarted() { return started;}

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
			if (playerInfos.get(i).getUsername() == player_info.getUsername()) {
				playerInfos.set(i, player_info);
				return;
			}
		}
		playerInfos.add(player_info);
	}

	public Player getMe() { return me; }
    public void setMe(Player me) { this.me = me; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board;}
    public void setLastCommandID(int lastID) { lastCommandID = lastID;}
	public List<String> getPlayers() {
		return players;
	}
	public void drawTrainCard(TrainCard cardDrawn) {me.drawTrainCard(cardDrawn);}
	public void addDestCard(DestCard card) {me.addDestCard(card);}
	public void placeRoute(String player_username, Route route, boolean hasLongest) {board.placeRoute(player_username, route, hasLongest);}

	public void addPlayer(String username) {
		if (players.indexOf(username) == -1) {
			players.add(username);
		}
	}

	public void setDestCardChoices(ArrayList<DestCard> choices) { me.setDestCardChoices(choices);}

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
}
