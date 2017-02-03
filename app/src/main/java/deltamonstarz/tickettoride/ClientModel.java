package deltamonstarz.tickettoride;

import java.util.List;

import delta.monstarz.Command;
import delta.monstarz.Player;

public class ClientModel {
	private static ClientModel clientModel = new ClientModel();
	private Player player;
	private String gameID;
	private int curCommand;
	private List<Command> gameHistory;

	private ClientModel() {}

	public static ClientModel getInstance() {
		return clientModel;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public int getCurCommand() {
		return curCommand;
	}

	public void setCurCommand(int curCommand) {
		this.curCommand = curCommand;
	}

	public List<Command> getGameHistory() {
		return gameHistory;
	}

	public void setGameHistory(List<Command> gameHistory) {
		this.gameHistory = gameHistory;
	}

	/**
	 * Gets game commands given after curCommand and executes them
	 */
	public void updateGame() {

	}
}
