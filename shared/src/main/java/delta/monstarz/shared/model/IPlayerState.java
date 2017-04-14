package delta.monstarz.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

public interface IPlayerState extends Serializable {
	void startTurn();

	void drawTrainCard(TrainCard card);

	void selectTrainCard(TrainCard card);

	void drawDestinationCards(ArrayList<DestCard> cards);

	void selectDestinationCards(ArrayList<DestCard> cards);

	void claimRoute(Route route, CardColor cardsUsed, int goldCardCount);

	boolean canDrawTrainCard();

	boolean canSelectTrainCard(TrainCard card);

	boolean canDrawDestinationCards();

	boolean canPlaceRoute(Route route);

	boolean mustDrawDestinationCard();

	boolean isTakingTurn();
}
