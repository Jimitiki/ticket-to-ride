package delta.monstarz;

/**
 * The GameInfo class exists for the purpose of sending information to the client
 * An instance of GameInfo contains basic information related to an instance of a game
 *
 */

public class GameInfo {

	private String name;
	private int gameID;
	private int startTime;
	private int playerCount;
	private boolean gameStarted;

	public GameInfo(String name, int gameID, int startTime, int playerCount, boolean gameStarted){
		this.name = name;
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

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
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
