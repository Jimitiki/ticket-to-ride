package delta.monstarz.shared.model;

import java.util.ArrayList;

abstract class PlayerState {
	void startTurn() {}

	void drawTrainCard(TrainCard card, boolean isFaceUp) {}

	void drawDestinationCards(ArrayList<DestCard> cards) {}

	void selectDestinationCards(ArrayList<DestCard> cards) {}

	void claimRoute(Route route) {}
}
