package deltamonstarz.tickettoride.model;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.Message;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;

/**
 * Created by oliphaun on 2/22/17.
 */

public class ClientGame {
    private Board board;
    private Player me;
    private List<Opponent> opps;
    private boolean started;
    private int lastCommandID;
    private int gameID;
	private List<String> players;
	private List<Message> chatHistory;

	public ClientGame(int id) {
		gameID = id;
		players = new ArrayList<>();
	}

    public int getGameID() { return gameID; }
    public void setGameID(int gameID) { this.gameID = gameID; }
    public int getCurCommand() { return lastCommandID; }
    public boolean isStarted() { return started;}

    public void setStarted(boolean started) {
		this.started = started;
		opps = new ArrayList<Opponent>();
	    chatHistory = new ArrayList<>();
		for (String username : players) {
			opps.add(new Opponent(username));
		}
	}

    public List<Opponent> getOpps() { return opps; }
    public void setOpps(List<Opponent> opps) { this.opps = opps; }
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

	public void setDestCardChoices(List<DestCard> choices) { me.setDestCardChoices(choices);}

	public List<DestCard> getDestCardChoices() {
		return me.getDestCardChoices();
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
