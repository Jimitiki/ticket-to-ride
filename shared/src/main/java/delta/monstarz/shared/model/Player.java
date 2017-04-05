package delta.monstarz.shared.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class Player {
	private String username;
	private PlayerColor playerColor;
	private int routeScore;
	private int numTrains;
	private int minSelection;
	private HashMap<CardColor, Integer> trainCards;
	private List<DestCard> destCards = new ArrayList<>();
	private ArrayList<DestCard> destCardChoices;
	protected IPlayerState state;
	private boolean hasLongest;

	public Player(String username) {
		this.username = username;
		trainCards = new HashMap<>();
		CardColor[] colors = CardColor.values();
		for (CardColor color : colors) {
			trainCards.put(color, 0);
		}
		hasLongest = false;
	}

	public PlayerResult getBasePlayerResult() {
		return new PlayerResult(username, playerColor, getScore(), routeScore, 0, 0, hasLongest); //score only contains the routeScore
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPlayerColor(PlayerColor my_pcolor) {
		playerColor = my_pcolor;
	}

	PlayerColor getPlayerColor() {
		return playerColor;
	}

	public int getScore() {
		int scorePlusLongest = routeScore;
		if (hasLongest) {
			scorePlusLongest += 10;
		}
		return scorePlusLongest;
	}

	public void setScore(int score) {
		this.routeScore = score;
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
		return state.isTakingTurn();
	}

	public HashMap<CardColor, Integer> getTrainCards() {
		return trainCards;
	}

	public ArrayList<DestCard> getDestCardChoices() {
		return destCardChoices;
	}

	public void setDestCardChoices(ArrayList<DestCard> destCardChoices) {
		state.drawDestinationCards(destCardChoices);
	}

	public void clearDestCardChoices(){
		destCardChoices = null;
	}

	public void startTurn() {
		state.startTurn();
	}

	protected void internalStartTurn() {

	}

	public void drawTrainCard(TrainCard card) {
		state.drawTrainCard(card);
	}

	protected void internalDrawTrainCard(TrainCard card) {

		if (card == null){
			return;
		}

		CardColor cardColor = card.getColor();
		if (! trainCards.containsKey(cardColor)) {
			trainCards.put(cardColor, 0);
		}
		trainCards.put(cardColor, trainCards.get(cardColor) + 1);
	}

	public void selectTrainCard(TrainCard card) {
		state.selectTrainCard(card);
	}

	public void drawDestinationCards(ArrayList<DestCard> cards) {
		state.drawDestinationCards(cards);
	}

	protected void internalDrawDestinationCards(ArrayList<DestCard> cards) {
		destCardChoices = cards;
	}

	public void selectDestinationCards(ArrayList<DestCard> cards) {
		state.selectDestinationCards(cards);
	}

	protected void internalSelectDestinationCards(ArrayList<DestCard> cards) {
		destCards.addAll(cards);
	}

	public void claimRoute(Route route, CardColor cardsUsed, int goldCardCount) {
		state.claimRoute(route, cardsUsed, goldCardCount);
	}

	protected void internalClaimRoute(Route route, CardColor cardsUsed, int goldCardCount) {
		int routeLength = route.getLength();
		int routeValue = (int) (.5 * routeLength * routeLength - 0.6 * routeLength + 1.4);
		numTrains -= routeLength;
		routeScore += routeValue;

		if (cardsUsed != CardColor.GOLD) {
			int numCardsUsed = trainCards.get(cardsUsed) - (route.getLength() - goldCardCount);
			trainCards.put(cardsUsed, numCardsUsed);
		}
		int numGoldCards = trainCards.get(CardColor.GOLD) - goldCardCount;
		trainCards.put(CardColor.GOLD, numGoldCards);
	}

	public boolean canDrawTrainCard(){
		return state.canDrawTrainCard();
	}

	public boolean canSelectTrainCard(TrainCard card){
		return state.canSelectTrainCard(card);
	}

	public int getGoldCardCount() {
		return trainCards.get(CardColor.GOLD);
	}

	public boolean canDrawDestinationCard(){
		return state.canDrawDestinationCards();
	}

	public boolean mustDrawDestinationCard(){
		return state.mustDrawDestinationCard();
	}

	public boolean canPlaceRoute(Route route) {
		return state.canPlaceRoute(route);
	}

	public void completeDestinationCard(DestCard card)
	{
		for (DestCard destCard : destCards) {
			if (destCard.equals(card)) {
				destCard.setCompleted(true);
			}
		}
	}

	public PlayerInfo playerInfo() {
		Collection<Integer> card_nums = trainCards.values();
		int numTrainsCards = 0;
		for (int n : card_nums) {
			numTrainsCards += n;
		}
		return new PlayerInfo(username, playerColor, getScore(), numTrainsCards, destCards.size(), numTrains, hasLongest, isTakingTurn());
	}

	public void setHasLongest(boolean hasLongest) {
		this.hasLongest = hasLongest;
	}
}
