package delta.monstarz.shared;

import java.util.List;

/**
 * @author bradcarter
 */

public interface IDestinationDeck
{
	/**
	 * Draws 3 cards from the deck for the player
	 */
	public List<DestinationCard> drawCards()
	{

	}

	/**
	 * Returns a card to the deck
	 */
	public void returnCard(DestinationCard card)
	{

	}

	/**
	 * Clears the deck
	 */
	public void clear()
	{

	}

}
