package deltamonstarz.tickettoride.model.player;

import java.util.ArrayList;

import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.IPlayerState;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;

/**
 * Created by Trevor on 3/21/2017.
 */

public abstract class ClientBasePlayerState implements IPlayerState {
	@Override
	public void startTurn() {

	}

	@Override
	public void drawTrainCard(TrainCard card) {

	}

	@Override
	public void selectTrainCard(TrainCard card) {

	}

	@Override
	public void drawDestinationCards(ArrayList<DestCard> cards) {

	}

	@Override
	public void selectDestinationCards(ArrayList<DestCard> cards) {

	}

	@Override
	public void claimRoute(Route route) {

	}

	@Override
	public boolean isTakingTurn() {
		return true;
	}
}
