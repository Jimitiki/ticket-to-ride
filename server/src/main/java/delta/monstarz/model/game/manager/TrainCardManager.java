package delta.monstarz.model.game.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import delta.monstarz.shared.model.TrainCard;

/**
 * @author bradcarter
 */
public class TrainCardManager
{
	//Data Members
	LinkedList<TrainCard> deck;
	ArrayList<TrainCard> faceupCards;

	//Constructor
	public TrainCardManager()
	{
		deck = new LinkedList<>();
		faceupCards = new ArrayList<>();
	}

	/**
	 * Adds a card to the face up selection, or the deck if that is full.
	 */
	public void addCard(TrainCard card)
	{
		if(faceupCards.size() < 5)
		{
			faceupCards.add(card);
		}
		else
		{
			deck.add(card);
		}
	}


	/**
	 * Places the faceup cards back in the deck, shuffles the deck, and then draws 5 new face cards
	 */
	public void shuffle()
	{
		deck.addAll(faceupCards);
		faceupCards.clear();
		Collections.shuffle(deck);
		for(int i = 0; i < 5; i++)
		{
			faceupCards.add(deck.removeFirst());
		}
	}

	public TrainCard drawCard()
	{
		return deck.removeFirst();
	}

	public List<TrainCard> drawCards(int number)
	{
		List<TrainCard> cards = new ArrayList<>();
		for(int i = 0; i < number; i++)
		{
			cards.add(drawCard());
		}
		return cards;
	}

	public List<TrainCard> getFaceUpCards()
	{
		return faceupCards;
	}

	public TrainCard drawFaceUpCard(int index)
	{
		if(index < 0 || index > 4)
		{
			//Throw Exception?
		}
		faceupCards.add(deck.removeFirst());
		return faceupCards.remove(index);
	}

	public void clear()
	{
		faceupCards.clear();
		deck.clear();
	}
}
