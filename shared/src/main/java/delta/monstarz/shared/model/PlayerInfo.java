package delta.monstarz.shared.model;

import delta.monstarz.shared.model.PlayerColor;

public class PlayerInfo {
	private String username;
	private PlayerColor playerColor;
	private int score;
	private int numTrainsCards;
	private int numDestCards;
	private int numTrains;
	private boolean hasLongestRoute;
	private boolean isPlayersTurn;

	public PlayerInfo(String username, PlayerColor playerColor, int score, int numTrainsCards,
	                  int numDestCards, int numTrains, boolean hasLongestRoute, boolean isPlayersTurn) {
		this.username = username;
		this.playerColor = playerColor;
		this.score = score;
		this.numTrainsCards = numTrainsCards;
		this.numDestCards = numDestCards;
		this.numTrains = numTrains;
		this.hasLongestRoute = hasLongestRoute;
		this.isPlayersTurn = isPlayersTurn;
	}


	public String getUsername() {
		return username;
	}

	public void setPlayerColor(PlayerColor my_pcolor) { playerColor = my_pcolor; }

	public PlayerColor getPlayerColor() { return playerColor; }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumTrainsCards() {
		return numTrainsCards;
	}

	public void setNumTrainsCards(int numTrainsCards) {
		this.numTrainsCards = numTrainsCards;
	}

	public int getNumDestCards() {
		return numDestCards;
	}

	public void setNumDestCards(int numDestCards) {
		this.numDestCards = numDestCards;
	}

	public int getNumTrains() {
		return numTrains;
	}

	public void setNumTrains(int numTrains) {
		this.numTrains = numTrains;
	}

	public boolean isHasLongestRoute() {
		return hasLongestRoute;
	}

	public void setHasLongestRoute(boolean hasLongestRoute) {
		this.hasLongestRoute = hasLongestRoute;
	}

	public boolean isPlayersTurn() {
		return isPlayersTurn;
	}

	public void setPlayersTurn(boolean playersTurn) {
		isPlayersTurn = playersTurn;
	}
}
