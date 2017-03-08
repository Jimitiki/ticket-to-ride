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
	static final int FACE_UP_COUNT = 5;


	//Data Members
	LinkedList<TrainCard> deck;
	ArrayList<TrainCard> faceupCards;

	//Constructor
	public TrainCardManager()
	{
		deck = new LinkedList<>();
		faceupCards = new ArrayList<>();
	}

	public void initialize(){
		shuffle();
		assignFaceUpCards();
	}

	private void assignFaceUpCards(){
		for (int i = 0; i < FACE_UP_COUNT; i++){
			faceupCards.add(drawCard());
		}
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
		return faceupCards;
	}

	public TrainCard drawFaceUpCard(int index)
	{
		TrainCard card = faceupCards.get(index);
		faceupCards.add(index, drawCard());
		return card;
	}

	public void clear()
	{
		faceupCards.clear();
		deck.clear();
	}
}
