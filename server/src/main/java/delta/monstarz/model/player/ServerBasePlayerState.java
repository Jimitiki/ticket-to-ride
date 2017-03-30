package delta.monstarz.model.player;

import java.util.ArrayList;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.IPlayerState;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;

public abstract class ServerBasePlayerState implements IPlayerState {
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
	public void claimRoute(Route route, CardColor cardsUsed, int goldCardCount) {

	}

	@Override
	public boolean canDrawTrainCard() {
		return false;
	}

	@Override
	public boolean canSelectTrainCard(TrainCard card) {
		return false;
	}

	@Override
	public boolean canDrawDestinationCards() {
		return false;
	}

	@Override
	public boolean canPlaceRoute(Route route) {
		return false;
	}

	@Override
	public boolean mustDrawDestinationCard() {
		return false;
	}

	@Override
	public boolean isTakingTurn() {
		return true;
	}
}
