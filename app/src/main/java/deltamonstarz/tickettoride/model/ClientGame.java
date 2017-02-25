package deltamonstarz.tickettoride.model;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Board;

/**
 * Created by oliphaun on 2/22/17.
 */

public class ClientGame {
    private Board board;
    private Player me;
    private List<Opponent> opps;
    private boolean started;
    private int commandCounter;
    private int gameID;
	private List<String> players;

	public ClientGame(int id) {
		gameID = id;
		players = new ArrayList<>();
	}

    public int getGameID() { return gameID; }

    public void setGameID(int gameID) { this.gameID = gameID; }

    public int getCurCommand() { return commandCounter; }

    public boolean isStarted() { return started;}

    public void setStarted(boolean started) {
		this.started = started;
		opps = new ArrayList<Opponent>();
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

    public void incrementCommand() { commandCounter++;}

	public List<String> getPlayers() {
		return players;
	}

	public void addPlayer(String username) {
		if (players.indexOf(username) == -1) {
			players.add(username);
		}
	}
}
