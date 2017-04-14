package delta.monstarz.model.game.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.TrainCard;

public class TrainCardManager implements Serializable
{
	private static final int FACE_UP_COUNT = 5;
	private int gameId;

	//Data Members
	private LinkedList<TrainCard> deck;
	private ArrayList<TrainCard> faceUpCards;

	//Constructor
	public TrainCardManager(JsonArray jsonTrainCards, int gameId)
	{
		this.gameId = gameId;
		deck = new LinkedList<>();
		faceUpCards = new ArrayList<>();
		for(int i = 0; i < jsonTrainCards.size(); i++)
		{
			JsonObject card = jsonTrainCards.get(i).getAsJsonObject();
			String color = card.get("color").getAsString();
			CardColor c = CardColor.fromString(color);
			int count = card.get("count").getAsInt();
			for(int j = 0; j < count; j++)
			{
				TrainCard trainCard = new TrainCard(c);
				deck.add(trainCard);
			}
		}
	}

	public void initialize(){
		shuffle();
		for (int i = 0; i < FACE_UP_COUNT; i++) {
			faceUpCards.add(null);
		}
		assignFaceUpCards();
	}

	/*
		Don't use this function in the constructor or the initialize function!!! Game will be null!!!
	 */
	private Game getGame() {
		return GameManager.getInstance().getGameByID(gameId);
	}


	public void assignFaceUpCards(){
		resetFaceUpCards();

		if (!isFaceUpCardsValid()) {
			fixFaceUpCards();
		}
	}

	public void returnCard(TrainCard card)
	{

		// If the deck is empty we need to do some extra work
		if (deck.size() == 0){

			// First fill up any empty face up card slots
			for (int i = 0; i < faceUpCards.size(); i++){
				if (faceUpCards.get(i) == null){
					faceUpCards.set(i, card);

					// Let the clients know
					SelectTrainCardCommand command = new SelectTrainCardCommand(null, gameId, i);
					command.setReplacementCard(card);
					getGame().addCommand(command);
					return;

				}
			}

			// Start filling the deck back up
			SelectTrainCardCommand command = new SelectTrainCardCommand(null, gameId, -1);
			command.setReplacementCard(new TrainCard(CardColor.GOLD)); // Color does not matter only that it is not null
			getGame().addCommand(command);
		}
		deck.add(card);
		shuffle();

		if (!isFaceUpCardsValid()) {
			fixFaceUpCards();
		}

	}

	public void shuffle()
	{
		Collections.shuffle(deck);
	}

	public TrainCard drawCard()
	{
		TrainCard card = null;


		if (deck.size() > 0 ){
			card = deck.removeFirst();
		}

		// If there are no more card in the deck we need to let the client know.
		if (deck.size() == 0){
			// Let the clients know that there are no cards left in the deck
			SelectTrainCardCommand command = new SelectTrainCardCommand(null, gameId, -1);
			getGame().addCommand(command);
		}

		return card;

	}

	public List<TrainCard> getFaceUpCards()
	{
		return faceUpCards;
	}

	// This function does not return what the new card is, use the getter like a man
	public void faceUpDestroyAndReplace(int index)
	{
		TrainCard replacementCard = drawCard();

		// If we don't have any cards in the deck then the face up card location should now be null
		if (replacementCard == null){
			SelectTrainCardCommand command = new SelectTrainCardCommand(null, gameId, index);
			getGame().addCommand(command);
		}


		faceUpCards.set(index, replacementCard);

		if (!isFaceUpCardsValid()) {
			fixFaceUpCards();
		}

	}

	private boolean isFaceUpCardsValid(){
		if (faceUpGoldCount() <= 2){
			return true;
		}


		int totalNonGoldCountInExistence = getNonGoldCountInManager();

		if ( totalNonGoldCountInExistence <= 2 ) {
			if (nonFaceUpGoldCount() != totalNonGoldCountInExistence){
				return false;
			}
			else {
				return true;
			}
		}

		return false;
	}

	private void fixFaceUpCards(){
		do {
			resetFaceUpCards();

		} while (!isFaceUpCardsValid());

		generateNewFaceUpCardCommands();
	}

	private void resetFaceUpCards(){
		for (TrainCard card: faceUpCards){
			if (card != null) {
				deck.add(card);
			}
		}
		shuffle();
		faceUpCards.clear();

		for (int i = 0; i < FACE_UP_COUNT; i++){
			faceUpCards.add(drawCard());
		}
	}

	private int getNonGoldCountInManager(){
		int count = 0;
		for (TrainCard card: deck){
			if (card.getColor() != CardColor.GOLD){
				count++;
			}
		}

		for (TrainCard card: faceUpCards){
			if (card != null && card.getColor() != CardColor.GOLD){
				count++;
			}
		}

		return count;
	}

	private void generateNewFaceUpCardCommands(){
		for (int i = 0; i < faceUpCards.size(); i++) {
			SelectTrainCardCommand command = new SelectTrainCardCommand(null, gameId, i);
			command.setReplacementCard(faceUpCards.get(i));
			getGame().addCommand(command);
		}
	}

	private int faceUpGoldCount(){
		int count = 0;
		for (TrainCard card: faceUpCards){
			if (card != null && card.getColor() == CardColor.GOLD){
				count++;
			}
		}
		return count;
	}

	private int nonFaceUpGoldCount(){
		int count = 0;
		for (TrainCard card: faceUpCards){
			if (card != null && card.getColor() != CardColor.GOLD){
				count++;
			}
		}
		return count;
	}

	public TrainCard getFaceUpCardByPosition(int index) {
		return faceUpCards.get(index);
	}

	public void clear()
	{
		faceUpCards.clear();
		deck.clear();
	}

	public boolean isDeckEmpty() {
		return deck.size() == 0;
	}
}
