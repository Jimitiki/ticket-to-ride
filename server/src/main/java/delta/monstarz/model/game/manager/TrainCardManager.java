package delta.monstarz.model.game.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.TrainCard;

public class TrainCardManager
{
	private static final int FACE_UP_COUNT = 5;


	//Data Members
	private LinkedList<TrainCard> deck;
	private ArrayList<TrainCard> faceUpCards;

	//Constructor
	public TrainCardManager(JsonArray jsonTrainCards)
	{
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

	public void assignFaceUpCards(){
		int numGoldCards;
		do {
			numGoldCards = 0;
			for (int i = 0; i < FACE_UP_COUNT; i++) {
				TrainCard card = drawCard();
				if (card.getColor() == CardColor.GOLD) {
					numGoldCards++;
				}
				faceUpCards.set(i, card);

			}
			if (numGoldCards >= 3) {
				for (TrainCard card : faceUpCards) {
					addCard(card);
				}
				shuffle();
			}
		} while (numGoldCards >= 3);
	}

	/**
	 * Adds a card to the deck.
	 */
	public void addCard(TrainCard card)
	{
		deck.add(card);
	}

	public void shuffle()
	{
		Collections.shuffle(deck);
	}

	public TrainCard drawCard()
	{
		return deck.removeFirst();
	}

	public List<TrainCard> getFaceUpCards()
	{
		return faceUpCards;
	}

	// This function does not return what the new card is, use the getter like a man (╯°□°）╯︵ ┻━┻
	public void faceUpDestoryAndReplace (int index)
	{
		faceUpCards.set(index, drawCard());
	}

	public TrainCard getFaceUpCardByPosition(int index) {
		return faceUpCards.get(index);
	}

	public void clear()
	{
		faceUpCards.clear();
		deck.clear();
	}
}
