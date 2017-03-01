package delta.monstarz.server;

import java.util.List;

import delta.monstarz.shared.model.ITrainDeck;
import delta.monstarz.shared.model.TrainCard;

/**
 * @author bradcarter
 */
public class TrainDeck implements ITrainDeck
{
	@Override
	public TrainCard drawCard()
	{
		return null;
	}

	@Override
	public List<TrainCard> drawCards(int number)
	{
		return null;
	}

	@Override
	public List<TrainCard> getFaceUpCards()
	{
		return null;
	}

	@Override
	public List<TrainCard> drawFaceUpCard(int index)
	{
		return null;
	}

	@Override
	public void clear()
	{

	}
}


/*




Players can draw 4 train cards during setup
The player may either take one of the face up cards or draw from the deck. If they take the face up card, they replace it with the top card from the deck before picking the second.
If player selects a locomotive from the face up cards, they may not select a second train card. If the replacement card is a locomotive, the player may not take it as their second card.
If at any time 3 of 5 face up cards are locomotives, the face up cards are discarded and 5 new ones are selected.
If the deck runs out, the discards are shuffled and reused. If there are no discards when the deck runs out, the player must draw a destination or claim a route.


*
*/