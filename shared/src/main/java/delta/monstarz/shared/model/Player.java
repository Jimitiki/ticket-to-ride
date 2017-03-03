package delta.monstarz.shared.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import delta.monstarz.shared.model.TrainCard;

/**
 * Created by Trevor on 2/2/2017.
 */

public class Player {
	private String username;
	private PlayerColor pcolor;
	private int score;
	private int numTrains;
	private Map<delta.monstarz.shared.model.TrainCard, Integer> trainCards;
	private List<DestCard> destCards;
	private List<DestCard> destCardChoices;

	public List<DestCard> getDestCardChoices() {
		return destCardChoices;
	}

	public void setDestCardChoices(List<DestCard> destCardChoices) {
		this.destCardChoices = destCardChoices;
	}

	public Player(String username){
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPcolor(PlayerColor my_pcolor) { pcolor = my_pcolor; }

	public Enum getPcolor() { return pcolor; }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumTrains() {
		return numTrains;
	}

	public void setNumTrains(int numTrains) {
		this.numTrains = numTrains;
	}

//	public Map<TrainCard, Integer> getTrainCards() {
//		return trainCards;
//	}
//
//	public void setTrainCards(Map<TrainCard, Integer> trainCards) {
//		this.trainCards = trainCards;
//	}

	public List<DestCard> getDestCards() {
		return destCards;
	}

	public void setDestCards(List<DestCard> destCards) {
		this.destCards = destCards;
	}

	public void drawTrainCard(TrainCard card) {
		trainCards.put(card, trainCards.get(card) + 1);
	}

	public void addDestCard(DestCard card) {
		destCards.add(card);
	}
}
