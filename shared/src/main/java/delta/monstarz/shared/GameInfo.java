package delta.monstarz.shared;

import java.util.Date;
import java.util.TreeSet;

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
	private TreeSet<String> players;


	public GameInfo(String name, String ownerName, int gameID, Date startTime, int playerCount, boolean gameStarted, TreeSet<String> players){
		this.name = name;
		this.ownerName = ownerName;
		this.gameID = gameID;
		this.startTime = startTime;
		this.playerCount = playerCount;
		this.gameStarted = gameStarted;
		this.players = players;
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

	public TreeSet<String> getPlayers() {
		return players;
	}

	public void setPlayers(TreeSet<String> players) {
		this.players = players;
	}

	/**
	 * Does not check if the creation date is the same
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameInfo == false){
			return false;
		}
		GameInfo b = (GameInfo) obj;

		if ( !this.name.equals(b.getName()) ){
			return false;
		}

		if ( !this.ownerName.equals(b.getOwnerName()) ){
			return false;
		}

		if ( this.gameID != b.getGameID() ){
			return false;
		}

		if ( this.playerCount != b.getPlayerCount() ){
			return false;
		}

		if ( this.gameStarted != b.isGameStarted() ){
			return false;
		}

		return true;
	}
}
