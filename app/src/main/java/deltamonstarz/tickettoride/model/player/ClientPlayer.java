package deltamonstarz.tickettoride.model.player;


import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.model.ClientGame;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.presenters.GamePresenter;

public class ClientPlayer extends Player {

	public ClientPlayer(String username) {
		super(username);
		state = new SetupState();
	}

	private boolean canDrawSecondCard(){
		ClientGame game = ClientModel.getInstance().getGame();
		List<TrainCard> faceUpCards = game.getFaceUpCards();

		for (TrainCard card: faceUpCards){
			if (card != null && card.getColor() != CardColor.GOLD){
				return true;
			}
		}
		return false;
	}

	//-----------------------------------------------------------------------------------
	private class SetupState extends ClientBasePlayerState {

		@Override
		public void drawTrainCard(TrainCard card) {
			internalDrawTrainCard(card);
			// State stays the same
		}

		@Override
		public void drawDestinationCards(ArrayList<DestCard> cards) {
			internalDrawDestinationCards(cards);
			state = new DestinationCardState();
		}

		@Override
		public void selectDestinationCards(ArrayList<DestCard> cards) {
			internalSelectDestinationCards(cards);
			state = new InactiveState();
		}

		@Override
		public boolean canDrawTrainCard() {
			return true;
		}

		@Override
		public boolean isTakingTurn() {
			return false;
		}
	}

	//-----------------------------------------------------------------------------------
	private class InactiveState extends ClientBasePlayerState {
		@Override
		public void startTurn() {
			internalStartTurn();
			state = new PlayerTurnState();
		}

		@Override
		public boolean isTakingTurn() {
			return false;
		}

	}

	//-----------------------------------------------------------------------------------
	private class PlayerTurnState extends ClientBasePlayerState {

		@Override
		public void drawTrainCard(TrainCard card) {
			internalDrawTrainCard(card);
			if (canDrawSecondCard()) {
				state = new TrainCardState();
			}
			else {
				state = new InactiveState();
			}
		}

		@Override
		public void selectTrainCard(TrainCard card) {
			internalDrawTrainCard(card);

			if (card.getColor() == CardColor.GOLD || !canDrawSecondCard()){
				state = new InactiveState();
			}
			else {
				state = new TrainCardState();
			}
		}

		@Override
		public void drawDestinationCards(ArrayList<DestCard> cards) {
			internalDrawDestinationCards(cards);
			if (cards.size() > 0) {
				state = new DestinationCardState();
			}
		}

		@Override
		public void claimRoute(Route route, CardColor cardsUsed, int goldCardsUsed) {
			if (getNumTrains() >= route.getLength()) {
				internalClaimRoute(route, cardsUsed, goldCardsUsed);
				state = new InactiveState();
			}
		}

		@Override
		public boolean canDrawTrainCard() {
			return true;
		}

		@Override
		public boolean canSelectTrainCard(TrainCard card) {
			return true;
		}

		@Override
		public boolean canDrawDestinationCards() {
			return true;
		}

		@Override
		public boolean canPlaceRoute() {
			return true;
		}
	}

	//-----------------------------------------------------------------------------------
	private class TrainCardState extends ClientBasePlayerState {

		@Override
		public void drawTrainCard(TrainCard card) {
			internalDrawTrainCard(card);
			state = new InactiveState();
		}

		@Override
		public void selectTrainCard(TrainCard card) {
			// Don't do anything if the card is gold
			if (card.getColor() != CardColor.GOLD){
				internalDrawTrainCard(card);
				state = new InactiveState();
			}
		}

		@Override
		public boolean canDrawTrainCard() {
			return true;
		}

		@Override
		public boolean canSelectTrainCard(TrainCard card) {
			if (card.getColor() == CardColor.GOLD){
				GamePresenter.getInstance().handleMessage(Toast.LENGTH_SHORT, "You can't draw a golden train card as your second card");
				return false;
			}
			else {
				return true;
			}
		}
	}

	//-----------------------------------------------------------------------------------
	private class DestinationCardState extends ClientBasePlayerState {
		@Override
		public void selectDestinationCards(ArrayList<DestCard> cards) {
			internalSelectDestinationCards(cards);
			state = new InactiveState();
		}

		@Override
		public boolean mustDrawDestinationCard() {
			return true;
		}
	}

}
