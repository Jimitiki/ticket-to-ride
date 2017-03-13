package delta.monstarz.shared.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Player {
	private String username;
	private PlayerColor playerColor;
	private int score;
	private int numTrains;
	private int minSelection;
	private boolean isTakingTurn;
	private HashMap<CardColor, Integer> trainCards;
	private List<DestCard> destCards = new ArrayList<>();
	private ArrayList<DestCard> destCardChoices;
	private PlayerState state;

	public Player(String username){
		this.username = username;
		trainCards = new HashMap<>();
		state = new InactiveState();
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPlayerColor(PlayerColor my_pcolor) { playerColor = my_pcolor; }

	public PlayerColor getPlayerColor() { return playerColor; }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumTrains() {
		return numTrains;
	}

	public void setNumTrains(int numTrains) {
		this.numTrains = numTrains;
	}

	public List<DestCard> getDestCards() {
		return destCards;
	}

	public int getMinSelection() {
		return minSelection;
	}

	public void setMinSelection(int minSelection) {
		this.minSelection = minSelection;
	}

	public boolean isTakingTurn() {
		return isTakingTurn;
	}

	public void setTakingTurn(boolean hasTurn) {
		this.isTakingTurn = hasTurn;
	}

	public HashMap<CardColor, Integer> getTrainCards(){
		return trainCards;
	}

	public ArrayList<DestCard> getDestCardChoices() {
		return destCardChoices;
	}

	public void setDestCardChoices(ArrayList<DestCard> destCardChoices) {
		this.destCardChoices = destCardChoices;
	}

	public void addDestCard(DestCard card) {
		destCards.add(card);
	}

	public void startTurn() {
		state.startTurn();
	}

	public void drawTrainCard(TrainCard card, boolean isFaceUp) {
		state.drawTrainCard(card, isFaceUp);
	}

	public void drawDestinationCards(ArrayList<DestCard> cards) {
		state.drawDestinationCards(cards);
	}

	public void selectDestinationCards(ArrayList<DestCard> cards) {
		state.selectDestinationCards(cards);
	}

	public void claimRoute(Route route) {
		state.claimRoute(route);
	}

	public PlayerInfo playerInfo() {
		Collection<Integer> card_nums = trainCards.values();
		int numTrainsCards = 0;
		for (int n : card_nums) {
			numTrainsCards += n;
		}

		return new PlayerInfo(username, playerColor, score, numTrainsCards, destCards.size(), numTrains, false, isTakingTurn);
	}

	private class InactiveState extends PlayerState {
		@Override
		public void startTurn() {
			state = new PlayerTurnState();
			isTakingTurn = true;
		}
	}

	private class PlayerTurnState extends PlayerState {
		@Override
		void drawTrainCard(TrainCard card, boolean isFaceUp) {
			CardColor cardColor = card.getColor();
			if (! trainCards.containsKey(cardColor)) {
				trainCards.put(cardColor, 0);
			}
			trainCards.put(cardColor, trainCards.get(cardColor) + 1);
			if (cardColor == CardColor.GOLD && isFaceUp) {
				state = new InactiveState();
				isTakingTurn = false;
			} else {
				state = new TrainCardState();
			}
		}

		@Override
		void drawDestinationCards(ArrayList<DestCard> cards) {
			destCardChoices = cards;
			state = new DestinationCardState();
		}

		@Override
		void claimRoute(Route route) {
			isTakingTurn = false;
			state = new InactiveState();
		}
	}

	private class TrainCardState extends PlayerState {
		@Override
		void drawTrainCard(TrainCard card, boolean isFaceUp) {
			if (card.getColor() != CardColor.GOLD || !isFaceUp) {
				CardColor cardColor = card.getColor();
				if (! trainCards.containsKey(cardColor)) {
					trainCards.put(cardColor, 0);
				}
				trainCards.put(cardColor, trainCards.get(cardColor) + 1);
			}
		}
	}

	private class DestinationCardState extends PlayerState {
		@Override
		void selectDestinationCards(ArrayList<DestCard> cards) {
			destCards.addAll(cards);
			state = new InactiveState();
		}
	}
}
