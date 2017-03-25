package delta.monstarz.model.player;

import java.util.ArrayList;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;

/**
 * Created by Trevor on 3/20/2017.
 */

public class ServerPlayer extends Player {

	private int gameId;

	public ServerPlayer(String username, int gameId) {
		super(username);
		this.gameId = gameId;
		state = new SetupState();
	}

	private void endTurn(){
		Game game = GameManager.getInstance().getGameByID(gameId);
		game.startNextPlayersTurn();
	}

	//-----------------------------------------------------------------------------------
	private class SetupState extends ServerBasePlayerState {

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
	private class InactiveState extends ServerBasePlayerState {
		public InactiveState() {
			endTurn();
		}

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
	private class PlayerTurnState extends ServerBasePlayerState {

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
	}

	//-----------------------------------------------------------------------------------
	private class TrainCardState extends ServerBasePlayerState {

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
		public boolean canSelectTrainCard(TrainCard card) {
			if (card.getColor() == CardColor.GOLD) {
				return false;
			} else {
				return true;
			}
		}
	}

	//-----------------------------------------------------------------------------------
	private class DestinationCardState extends ServerBasePlayerState {
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
