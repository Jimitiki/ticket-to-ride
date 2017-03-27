package delta.monstarz.shared.model;

import java.util.ArrayList;

public interface IPlayerState {
	void startTurn();

	void drawTrainCard(TrainCard card);

	void selectTrainCard(TrainCard card);

	void drawDestinationCards(ArrayList<DestCard> cards);

	void selectDestinationCards(ArrayList<DestCard> cards);

	void claimRoute(Route route, CardColor cardsUsed, int goldCardCount);

	boolean canDrawTrainCard();

	boolean canSelectTrainCard(TrainCard card);

	boolean canDrawDestinationCards();

	boolean canPlaceRoute();

	boolean isTakingTurn();
}
