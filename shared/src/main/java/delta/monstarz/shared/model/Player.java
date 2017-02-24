package delta.monstarz.shared.model;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Trevor on 2/2/2017.
 */

public class Player extends IPlayer {
	private Map<TrainCard, Integer> trainCards;
	private Collection<DestCard> destCards;

	public Player(String username){
		this.username = username;
	}

//	public Map<TrainCard, Integer> getTrainCards() {
//		return trainCards;
//	}
//
//	public void setTrainCards(Map<TrainCard, Integer> trainCards) {
//		this.trainCards = trainCards;
//	}

	public Collection<DestCard> getDestCards() {
		return destCards;
	}

	public void setDestCards(Collection<DestCard> destCards) {
		this.destCards = destCards;
	}
}
