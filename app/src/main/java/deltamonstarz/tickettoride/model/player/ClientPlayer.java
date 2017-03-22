package deltamonstarz.tickettoride.model.player;


import java.util.ArrayList;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;

/**
 * Created by Trevor on 3/20/2017.
 */

public class ClientPlayer extends Player {

	public ClientPlayer(String username) {
		super(username);
		state = new SetupState();
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
			state = new TrainCardState();
		}

		@Override
		public void selectTrainCard(TrainCard card) {
			internalDrawTrainCard(card);

			if (card.getColor() == CardColor.GOLD){
				state = new InactiveState();
			}
			else {
				state = new InactiveState();
			}
		}

		@Override
		public void drawDestinationCards(ArrayList<DestCard> cards) {
			internalDrawDestinationCards(cards);
			state = new DestinationCardState();
		}

		@Override
		public void claimRoute(Route route) {
			// Todo: Add functionality here
			state = new InactiveState();
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
	}

	//-----------------------------------------------------------------------------------
	private class DestinationCardState extends ClientBasePlayerState {
		@Override
		public void selectDestinationCards(ArrayList<DestCard> cards) {
			internalSelectDestinationCards(cards);
			state = new InactiveState();
		}
	}

}
