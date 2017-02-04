package delta.monstarz.shared;

import java.util.Date;

/**
 * The GameInfo class exists for the purpose of sending information to the client
 * An instance of GameInfo contains basic information related to an instance of a game
 *
 */

public class GameInfo {

	private String name;
	private String ownerName;
	private int gameID;
	private Date startTime;
	private int playerCount;
	private boolean gameStarted;

	public GameInfo(String name, String ownerName, int gameID, Date startTime, int playerCount, boolean gameStarted){
		this.name = name;
		this.ownerName = ownerName;
		this.gameID = gameID;
		this.startTime = startTime;
		this.playerCount = playerCount;
		this.gameStarted = gameStarted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
}
