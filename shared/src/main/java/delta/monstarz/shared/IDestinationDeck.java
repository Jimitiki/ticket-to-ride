package delta.monstarz.shared;

import java.util.List;

import delta.monstarz.shared.model.DestCard;

/**
 * @author bradcarter
 */

public interface IDestinationDeck
{
	/**
	 * Draws 3 cards from the deck for the player
	 */
	public List<DestCard> drawCards();

	/**
	 * Returns a card to the deck
	 */
	public void returnCard(DestCard card);

	/**
	 * Clears the deck
	 */
	public void clear();

}
