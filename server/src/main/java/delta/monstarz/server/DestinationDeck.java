package delta.monstarz.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import delta.monstarz.shared.model.IDestinationDeck;
import delta.monstarz.shared.model.DestCard;

/**
 * @author bradcarter
 */
public class DestinationDeck implements IDestinationDeck
{
	//Data Members
	LinkedList<DestCard> deck;

	//Constructor
	public DestinationDeck() {
		deck = new LinkedList<>();
	}

	public void addCard(DestCard card) {
		deck.add(card);
	}

	/**
	 * Shuffles the deck of destination cards
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * Takes three cards from the destination deck
	 *
	 * @return Three destination cards in a list
	 */
	@Override
	public List<DestCard> drawCards() {
		List<DestCard> cards = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			cards.add(deck.removeFirst());
		}
		return cards;
	}

	/**
	 * Returns a card to the deck.
	 */
	@Override
	public void returnCard(DestCard card) {
		deck.addLast(card);
	}

	/**
	 * Returns a list of cards to the deck.
	 */
	@Override
	public void returnCards(List<DestCard> cards) {
		for(DestCard card : cards) {
			returnCard(card);
		}
	}

	/**
	 * Removes all cards from the deck.
	 */
	@Override
	public void clear()
	{
		deck = new LinkedList<>();
	}
}
