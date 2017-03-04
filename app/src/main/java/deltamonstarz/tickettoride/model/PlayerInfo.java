package deltamonstarz.tickettoride.model;

public class PlayerInfo {
	private String username;
	private Enum pcolor;
	private int score;
	private int numTrainsCards;
	private int numDestCards;
	private int numTrains;

	public PlayerInfo(String username) {
		this.username = username;
		numDestCards = numTrainsCards = score = 0;
		numTrains = 45;
	}

	public String getUsername() {
		return username;
	}

	public void setPcolor(Enum my_pcolor) { pcolor = my_pcolor; }

	public Enum getPcolor() { return pcolor; }

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
}
