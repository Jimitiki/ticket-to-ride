package delta.monstarz.model.game.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.TrainCard;

/**
 * @author bradcarter
 */
public class TrainCardManager
{
	static final int FACE_UP_COUNT = 5;


	//Data Members
	LinkedList<TrainCard> deck;
	ArrayList<TrainCard> faceUpCards;

	//Constructor
	public TrainCardManager()
	{
		deck = new LinkedList<>();
		faceUpCards = new ArrayList<>();
	}

	public void initialize(){
		shuffle();
		assignFaceUpCards();
	}

	public void assignFaceUpCards(){
		int numGoldCards = 0;
		do {
			for (int i = 0; i < FACE_UP_COUNT; i++) {
				TrainCard card = drawCard();
				if (card.getColor() == CardColor.GOLD) {
					numGoldCards++;
				}
				faceUpCards.set(i, drawCard());

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
	 * Adds a card to the face up selection, or the deck if that is full.
	 */
	public void addCard(TrainCard card)
	{
		deck.add(card);
	}


	/**
	 * Places the faceup cards back in the deck, shuffles the deck, and then draws 5 new face cards
	 */
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

	public TrainCard drawFaceUpCard(int index)
	{
		TrainCard card = faceUpCards.get(index);
		faceUpCards.add(index, drawCard());
		return card;
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
