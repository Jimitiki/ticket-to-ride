package delta.monstarz.shared;


import java.util.List;

import delta.monstarz.shared.model.TrainCard;

/**
 * @author bradcarter
 */

public interface ITrainDeck
{
	/**
	 * Removes a card from the deck to give to the player
	 */
	public TrainCard drawCard();

	/**
	 *
	 */
	public List<TrainCard> drawCards(int number);

	/**
	 *
	 */
	public List<TrainCard> getFaceUpCards();

	/**
	 *
	 */
	public List<TrainCard> drawFaceUpCard(int index);

	/**
	 * Removes all cards from the deck.
	 */
	public void clear();
}
