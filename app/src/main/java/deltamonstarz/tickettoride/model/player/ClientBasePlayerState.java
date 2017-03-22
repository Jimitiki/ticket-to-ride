package deltamonstarz.tickettoride.model.player;

import java.util.ArrayList;

import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.IPlayerState;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.presenters.GamePresenter;

/**
 * Created by Trevor on 3/21/2017.
 */

public abstract class ClientBasePlayerState implements IPlayerState {
	@Override
	public void startTurn() {

	}

	@Override
	public void drawTrainCard(TrainCard card) {
		GamePresenter.getInstance().handleMessage("You can't draw a train card when it is not your turn");
	}

	@Override
	public void selectTrainCard(TrainCard card) {
		GamePresenter.getInstance().handleMessage("You can't select a train card when it is not your turn");
	}

	@Override
	public void drawDestinationCards(ArrayList<DestCard> cards) {
		GamePresenter.getInstance().handleMessage("You can't choose more destination cards when it is not your turn");
	}

	@Override
	public void selectDestinationCards(ArrayList<DestCard> cards) {
		GamePresenter.getInstance().handleMessage("You can't choose more destination cards when it is not your turn");
	}

	@Override
	public void claimRoute(Route route) {
		GamePresenter.getInstance().handleMessage("You can't claim a route when it is not your turn");
	}

	@Override
	public boolean canDrawTrainCard() {
		GamePresenter.getInstance().handleMessage("You can't draw a train card when it is not your turn");
		return false;
	}

	@Override
	public boolean canSelectTrainCard(TrainCard card) {
		GamePresenter.getInstance().handleMessage("You can't select a train card when it is not your turn");
		return false;
	}

	@Override
	public boolean canDrawDestinationCards() {
		return false;
	}

	@Override
	public boolean canPlaceRoute() {
		return false;
	}

	@Override
	public boolean isTakingTurn() {
		return true;
	}
}
