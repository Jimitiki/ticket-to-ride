package delta.monstarz.shared.model;

import java.util.ArrayList;

/**
 * Created by Trevor on 3/21/2017.
 */

public interface IPlayerState {
	void startTurn();

	void drawTrainCard(TrainCard card);

	void selectTrainCard(TrainCard card);

	void drawDestinationCards(ArrayList<DestCard> cards);

	void selectDestinationCards(ArrayList<DestCard> cards);

	void claimRoute(Route route);

	boolean isTakingTurn();
}
